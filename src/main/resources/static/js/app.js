let selecionado = null;
let linhaSelecionada = null;

const API_KEY = "I_Love_Milfs";

const tituloInput = document.getElementById("tituloInput");
const autorInput = document.getElementById("autorInput");
const generoInput = document.getElementById("generoInput");
const dataInput = document.getElementById("dataInput");
const pdfInput = document.getElementById("pdfInput");

// máscara da data (dd/MM/yyyy – máx 8 números)
document.addEventListener("input", function (e) {
  if (e.target.id === "dataInput") {
    let v = e.target.value.replace(/\D/g, "").substring(0, 8);
    if (v.length >= 5) v = v.substring(0,2)+"/"+v.substring(2,4)+"/"+v.substring(4);
    else if (v.length >= 3) v = v.substring(0,2)+"/"+v.substring(2);
    e.target.value = v;
  }
});

function toISO(ddmmyyyy) {
  if (!/^\d{2}\/\d{2}\/\d{4}$/.test(ddmmyyyy)) return null;
  const [d, m, y] = ddmmyyyy.split("/");
  return `${y}-${m}-${d}`;
}

function inserir() {
  const titulo = tituloInput.value.trim();
  const autor = autorInput.value.trim();
  const genero = generoInput.value.trim();
  const data = dataInput.value.trim();
  const pdf = pdfInput.files[0];

  const iso = toISO(data);
  if (!titulo || !autor || !iso || !pdf) {
    alert("Campos inválidos");
    return;
  }

  const form = new FormData();
  form.append("titulo", titulo);
  form.append("autor", autor);
  form.append("genero", genero);
  form.append("dataPublicacao", iso);
  form.append("pdf", pdf);

  fetch("/api/livros", {
    method: "POST",
    headers: {
      "X-API-KEY": API_KEY
    },
    body: form
  })
  .then(response => {
    if (!response.ok) {
      throw new Error("Erro HTTP: " + response.status);
    }
    return response.json();
  })
  .then(() => {
    location.href = "/listar";
  })
  .catch(err => {
    console.error(err);
    alert("Erro ao inserir livro");
  });
}

function carregar() {
  fetch("/api/livros", {
    method: "GET",
    headers: {
      "X-API-KEY": API_KEY,
      "Accept": "application/json"
    }
  })
    .then(r => {
      if (!r.ok) throw new Error("Erro HTTP: " + r.status);
      return r.json();
    })
    .then(livros => {
      const tabela = document.getElementById("tabelaLivros");
      tabela.innerHTML = "";

      livros.forEach(l => {
        const tr = document.createElement("tr");
        tr.onclick = () => selecionar(tr, l.id);
        tr.innerHTML = `
          <td>${l.id}</td>
          <td>${l.titulo}</td>
          <td>${l.autor}</td>
          <td>${l.genero ?? ""}</td>
          <td>${l.dataPublicacao ? l.dataPublicacao.split("-").reverse().join("/") : ""}</td>
        `;
        tabela.appendChild(tr);
      });
    })
    .catch(err => {
      console.error(err);
      alert("Erro ao carregar livros");
    });
}

function selecionar(tr, id) {
  if (linhaSelecionada) linhaSelecionada.classList.remove("selecionado");
  tr.classList.add("selecionado");
  linhaSelecionada = tr;
  selecionado = id;
}

function excluir() {
  if (!selecionado) {
    alert("Selecione um livro");
    return;
  }

  fetch(`/api/livros/${selecionado}`, {
    method: "DELETE",
    headers: {
      "X-API-KEY": API_KEY
    }
  })
    .then(response => {
      if (!response.ok) throw new Error("Erro ao excluir");
      carregar();
    })
    .catch(err => {
      console.error(err);
      alert("Erro ao excluir livro");
    });
}

if (document.getElementById("tabelaLivros")) {
  carregar();
}
