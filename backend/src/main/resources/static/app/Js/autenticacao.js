document.addEventListener("DOMContentLoaded", () => {
  // Cadastro
  document.querySelector('#signupModal .btn-success')?.addEventListener('click', () => {
    const nome = document.querySelector('#signupModal input[placeholder="Nome completo"]')?.value;
    const email = document.querySelector('#signupModal input[placeholder="Email"]')?.value;
    const senha = document.querySelector('#signupModal input[placeholder="Senha"]')?.value;

    if (nome && email && senha) {
      const usuario = { nome, email, senha };

      fetch('http://localhost:8080/usuarios', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(usuario)
      })
        .then(response => {
          if (!response.ok) {
            throw new Error('Erro ao cadastrar usuário');
          }
          return response.json();
        })
        .then(data => {
          alert('Cadastro realizado com sucesso!');
          const modal = bootstrap.Modal.getInstance(document.querySelector('#signupModal'));
          modal?.hide();
        })
        .catch(error => {
          console.error(error);
          alert('Erro no cadastro. Tente novamente.');
        });

      const modal = bootstrap.Modal.getInstance(document.querySelector('#signupModal'));
      modal?.hide();
    } else {
      alert('Preencha todos os campos.');
    }
  });

  // Login
  document.querySelector('#loginModal .btn-primary')?.addEventListener('click', () => {
    const email = document.querySelector('#loginModal input[placeholder="Email"]')?.value;
    const senha = document.querySelector('#loginModal input[placeholder="Senha"]')?.value;

    fetch("http://localhost:8080/usuarios", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ email, senha })
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

  const usuarioId = localStorage.getItem("usuarioId");
  if (usuarioId) {
    fetch(`http://localhost:8080/usuarios/${usuarioId}`)
      .then(res => {
        if (!res.ok) throw new Error("Erro ao buscar usuário");
        return res.json();
      })
      .then(usuarioLogado => {

        const sidebar = document.getElementById('sidebar');
        const perfilBtn = document.createElement('div');
        perfilBtn.innerHTML = ``;
        sidebar.appendChild(perfilBtn);

        document.getElementById('btnSair')?.addEventListener('click', () => {
          sessionStorage.removeItem('usuarioId');
          location.reload();
        });

        const navbarRight = document.getElementById('navbar-user-area');
        if (navbarRight) {
          navbarRight.innerHTML = `
          <div class="d-flex align-items-center gap-2">
              <a href="criar-post.html" class="btn btn-success btn-sm">+ Criar</a>
              <div class="dropdown">
              <button class="btn btn-outline-light btn-sm dropdown-toggle" type="button" id="userDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                  ${usuarioLogado.nome}
              </button>
              <ul class="dropdown-menu dropdown-menu-end bg-dark text-white" aria-labelledby="userDropdown">
                  <li><a class="dropdown-item text-white" href="perfil.html">Meu Perfil</a></li>
                  <li><a class="dropdown-item text-danger" href="#" id="btnSairDropdown">Sair</a></li>
              </ul>
              </div>
          </div>`;
        }

        document.getElementById('btnSairDropdown')?.addEventListener('click', () => {
          localStorage.removeItem('usuarioId');
          location.reload();
        });
      })
      .catch(err => {
        console.error("Erro ao carregar dados do usuário", err);
        sessionStorage.removeItem("usuarioId");
      });
  }

  // Redirecionar para postagens com filtro por gênero
  document.querySelectorAll('.dropdown-item').forEach(item => {
    item.addEventListener('click', event => {
      const genero = item.textContent.trim();
      if (item.getAttribute('href') === '#') {
        event.preventDefault();
        window.location.href = `postagens.html?genero=${encodeURIComponent(genero)}`;

      }
    });
  });
});
//acho que assim vai... Que Deus nos ajude...