let selecionado = null;
let linhaSelecionada = null;

const tituloInput = document.getElementById("tituloInput");
const autorInput = document.getElementById("autorInput");
const generoInput = document.getElementById("generoInput");
const dataInput = document.getElementById("dataInput");
const pdfInput = document.getElementById("pdfInput");

// máscara da data
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
  const [d,m,y] = ddmmyyyy.split("/").map(Number);
  const date = new Date(y, m - 1, d);
  if (date.getFullYear() !== y || date.getMonth() !== m - 1 || date.getDate() !== d) return null;
  return `${y}-${String(m).padStart(2,'0')}-${String(d).padStart(2,'0')}`;
}

function inserir() {
  const titulo = tituloInput.value.trim();
  const autor = autorInput.value.trim();
  const genero = generoInput.value.trim();
  const data = dataInput.value.trim();
  const pdf = pdfInput.files[0];

  const iso = toISO(data);
  if (!titulo || !autor || !iso || !pdf) {
    alert("Campos inválidos ou data impossível");
    return;
  }

  const form = new FormData();
  form.append("titulo", titulo);
  form.append("autor", autor);
  form.append("genero", genero);
  form.append("dataPublicacao", iso);
  form.append("pdf", pdf);

  fetch("/api/livros", { method:"POST", body:form })
    .then(res => {
      if(!res.ok) return res.text().then(text => { throw new Error(text) });
      return res.json();
    })
    .then(() => {
      alert(`Livro "${titulo}" cadastrado com sucesso!`);
      tituloInput.value = "";
      autorInput.value = "";
      generoInput.value = "";
      dataInput.value = "";
      pdfInput.value = "";
      carregar();
    })
    .catch(e => alert("Erro ao inserir: " + e.message));
}

function carregar() {
  fetch("/api/livros")
    .then(res => {
      if(!res.ok) return res.text().then(text => { throw new Error(text) });
      return res.json();
    })
    .then(livros => {
      const tabela = document.getElementById("tabelaLivros");
      if (!tabela) return;
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
    .catch(e => console.error("Erro ao listar livros:", e.message));
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

  fetch(`/api/livros/${selecionado}`, { method:"DELETE" })
    .then(() => {
      alert("Livro excluído com sucesso!");
      carregar();
    })
    .catch(e => alert("Erro ao excluir: " + e.message));
}

if (document.getElementById("tabelaLivros")) {
  carregar();
}
