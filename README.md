# ReservasJá - Gestão de Hospedagem

O ReservasJá é um sistema desenvolvido para modernizar e escalar o processo de aluguel de quartos na Península de Maraú. O projeto foca em transformar residências locais em opções de hospedagem organizadas, utilizando uma arquitetura robusta e práticas modernas de desenvolvimento.

---

## Estrutura do Projeto

```bash
/frontend  → Interface (HTML, CSS, JS)
/backend   → API (Java + Spring Boot)
```

## Tecnologias Utilizadas

- Linguagem: Java 
- Framework: Spring Boot (API REST) 
- Arquitetura: Camadas (Controller, Service, Repository, Model) 
- Persistência de Dados: MySQL 
- Testes: 

## Diagrama de Classe

![Diagrama de Classe](docs/uml/Diagrama.png)

---

## Cartões CRC

[Clique aqui para acessar o Cartão CRC](docs/crc/Cartões%20CRC.pdf)

---

## Telas


![Tela home](docs/screens/reservasja-pagina-home.png)

![Tela login](docs/screens/reservasja-tela-login.png)

![Tela hospede](docs/screens/reservasja-pagina-hospede.png)

![Tela anfitrião](docs/screens/reservasja-pagina-anfitriao.png)

---

## Soluções Arquiteturais

### Singleton

O padrão Singleton foi aplicado na classe ConfiguracaoGlobalSistema para centralizar as regras globais do sistema.

A existência de múltiplas instâncias dessa configuração poderia gerar inconsistências, como diferentes regras de cálculo sendo aplicadas simultaneamente em partes distintas do sistema.

O Singleton garante que todas as classes compartilhem a mesma instância de configuração, promovendo:

- Consistência nas regras de negócio
- Facilidade de manutenção
- Alterações centralizadas

Isso resolve o problema de duplicação de regras e evita divergências no comportamento do sistema.

### Strategy

### Observer

