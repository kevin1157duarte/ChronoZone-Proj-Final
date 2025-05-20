
// Login
document.querySelector('#loginModal .btn-primary')?.addEventListener('click', () => {
  const email = document.querySelector('#loginModal input[placeholder="Email"]')?.value;
  const senha = document.querySelector('#loginModal input[placeholder="Senha"]')?.value;

  fetch("http://localhost:8080/usuarios", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ email, senha }) // loginRequest
  })
  .then(res => {
    if (!res.ok) throw new Error("Credenciais inválidas");  
    return res.json();
  })
  .then(usuario => {
    localStorage.setItem("usuarioId", usuario.id);
    alert("Login realizado com sucesso!");
    const modal = bootstrap.Modal.getInstance(document.querySelector('#loginModal'));
    modal?.hide();
    location.reload();
  })
  .catch(err => {
    alert("Email ou senha incorretos.");
  });
});

