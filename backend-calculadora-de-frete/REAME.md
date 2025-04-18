# 🚚 Backend - Calculadora de Frete API

API Spring Boot que calcula frete baseado no CEP.

## 🛠️ Tecnologias Principais
- **Java 21**
- **Spring Boot 3.4.4**
  - Spring Web
  - Spring Validation
  - Spring DevTools
- **ViaCEP** (API externa)

## 🌐 Sobre a API
- **Tipo**: RESTful JSON
- **Endpoint**: `POST /api/frete/calcular`
- **Request**:
  ```json  
  { "cep": "01001000" }  

## 🚀 Como rodar
```bash
  mvn spring-boot:run
```

## ⚙️ Variáveis de ambiente
Crie um application.properties baseado no application.properties.example.
