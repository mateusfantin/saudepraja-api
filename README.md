# saudepraja-api
Rest api for scheduling medical exams

O que é necessário para o build do projeto:
1- IDE (Eclipse, IntelliJ).
2- Java na versão 21.
3- Maven versão (3.9.11).


Para executar essa aplicação utilizando o docker faça:
* Necessário o docker desktop instalado.
* O arquivo example.env.txt deve ser renomeado para: .env
* Esse arquivo deve ter esse nome mesmo para o docker compose conseguir ler as variáveis, então não coloque nada antes do ponto.

1- Vá até a pasta que está a aplicação.
2- Abra um terminal da sua preferência.
3- O seguinte comando vai limpar e depois fazer o build do projeto, para isso digite: mvn clean package
4- Em seguida faremos o build da imagem no docker, digite o seguinte comando: docker build -t saudepraja-api
5- Por último fazemos o docker compose erguer o banco de dados e a aplicação. Digite: docker compose up

* Para parar o docker podemos apertar stop no docker desktop ou digitar: docker compose down.