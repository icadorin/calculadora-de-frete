# 📦 Calculadora de Frete

Aplicação simples desenvolvida com Java e Spring Boot para calcular o frete. A API ViaCEP será consumida e aplicada uma lógica própria para definir o valor do frete conforme o estado.

## 🌐 Acesso
- **Frontend (Vercel)**: [ https://calcular-frete-ic.vercel.app ].

## 🧠 Como funciona

- Informar o CEP de destino;
- A API consulta o ViaCEP para descobrir o estado do CEP informado;
- Com base no estado, a aplicação calcula o valor do frete.

## 💸 Regras de Frete

**Fórmula**:  
`Frete = (Preço base do estado) × (Fator da região)`

### 🗂️ Fatores por Região
| Região       | Multiplicador | Exemplo (UF)       |
|--------------|--------------:|--------------------|
| **Norte**    | 1.8×          | AM (R$27 → R$48,60)|
| **Nordeste** | 1.5×          | BA (R$16 → R$24)   |
| **Sudeste**  | 1.0×          | SP (R$10 → R$10)   |

## 🔌 Endpoint principal

### POST `/`

Calcula o valor do frete conforme o CEP informado.


## 🛠️ Tecnologias utilizadas

- Java;

- Spring Boot;

- API REST;

- Consumo de API externa (ViaCEP).

## 🌐 Deploy
A aplicação será hospedada para ser acessada de forma online.
