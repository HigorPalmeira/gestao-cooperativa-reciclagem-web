# Sistema de Gestão de Cooperativa de Reciclagem

## 1. Apresentação

Este projeto tem como objetivo principal transformar a gestão operacional de cooperativas de reciclagem em um processo eficiente, transparente e auditável. O sistema permite o registro e controle do fluxo de materiais, desde a sua entrada como “Lote Bruto”, passando pela triagem e classificação, até a sua transformação em “Matéria-Prima” (Lote Processado) e consequentemente venda para a indústria. 

O sistema busca fornecer um controle de estoque, controle do fluxo de materiais e gestão financeira, promovendo maior eficiência, transparência e rentabilidade para as cooperativas.

## 2. Tecnologias Utilizadas

O sistema foi projetado como uma **Aplicação Web Centralizada**, desenvolvida sobre a plataforma Java (Jakarta EE) e executada em servidor Apache Tomcat, utilizando a IDE Eclipse EE para uso do Java Servlets e o banco de dados relacional MySQL.

* **Linguagens de estilo e marcação**: 
  * JSP (JavaServer Pages): Linguagem de marcação (estrutura do conteúdo) e script; e, 
  * CSS: Linguagem de estilo (aparência do conteúdo).
* **Linguagens de programação**: Java e JavaScript
* **Frameworks ou plataformas**: Eclipse EE.
* **Gerência de Dados**: MySQL Community Server - GPL (Versão 8.0 para Windows)

## 3. Plataforma de Implementação

A plataforma de implementação do Sistema de Gestão de Cooperativa de Reciclagem baseia-se em uma arquitetura Cliente-Servidor projetada para o ambiente Web. O funcionamento do sistema é dividido em duas partes de execução:

* Do usuário final, o sistema é multiplataforma. A implementação não exige a instalação de softwares específicos nas máquinas locais. O acesso às interfaces requer apenas um dispositivo com um navegador web moderno compatível com renderização de HTML5, CSS e execução de scripts locais em JavaScript.
* O código-fonte foi estruturado utilizando o padrão de projeto arquitetural MVC (Model-View-Controller), visando a clara separação entre as regras de negócio da cooperativa, a interface visual e o controle do fluxo de dados.

Para a implementação e execução em ambiente de produção, a plataforma exige:
* Um servidor com o ambiente de execução Java instalado.
* Um contêiner web de servlets ativo, Apache Tomcat, responsável por interceptar as requisições HTTP da internet, compilar as páginas JSP dinamicamente e gerenciar as sessões de login dos usuários.
* Uma instância do servidor de banco de dados relacional ativa na mesma rede ou nuvem, ouvindo na porta padrão, para garantir o processamento em tempo real das transações, controle de estoque e registros de lotes da cooperativa.

