# Sistema de Gestão de Cooperativa de Reciclagem

> O documento ainda não está completo, mas abrange muito sobre o projeto.

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

## 4. Sistema

O banco de dados do sistema tem a seguinte organização dos dados:

![Figura 1 - Diagrama Entidade-Relacionamento do banco de dados do sistema](https://github.com/HigorPalmeira/gestao-cooperativa-reciclagem-web/blob/main/eer-gestao-cooperativa-de-reciclagem.png?raw=true)

Descrição das entidades:

* A entidade “Categoria de Processamento” será responsável por armazenar os dados: ID, nome e descrição da categoria.
* A entidade “Cliente” será responsável por armazenar os dados: CNPJ e nome da empresa, contato principal, e-mail para contato e data de cadastro.
* A entidade “Fornecedor” será responsável por armazenar os dados: documento (CPF/CNPJ), nome e tipo do fornecedor e data de cadastro.
* A entidade “Usuário” será responsável por armazenar os dados: ID do usuário, nome, e-mail, senha e papel.
* A entidade “Tipo de Material” será responsável por armazenar os dados: ID, nome e descrição do material.
* A entidade “Lote Bruto” será responsável por armazenar os dados: ID, peso de entrada (kg), data de entrada, status e o fornecedor do lote bruto. A entidade “Lote Bruto” pode ter apenas 1 “Fornecedor”. Já a entidade “Fornecedor” pode ter 1 ou mais “Lote Bruto”.
* A entidade “Transação de Compra” será responsável por armazenar os dados: ID, valor total calculado, status de pagamento, data de cálculo e de pagamento e lote bruto. A entidade “Transação de Compra” pode ter apenas 1 “Lote Bruto”. Já a entidade “Lote Bruto” pode ter 1 ou mais “Transação de Compra”.
* A entidade “Preço do Material” será responsável por armazenar os dados: ID, preço de compra, data de vigência e tipo de material. A entidade “Preço do Material” pode ter apenas 1 “Tipo de Material”. Já a entidade “Tipo de Material” pode ter 1 ou mais “Preço do Material”.
* A entidade “Venda” será responsável por armazenar os dados: ID, data, valor total e cliente da venda. A entidade “Venda” pode ter apenas 1 “Cliente”. Já a entidade “Cliente” pode ter 1 ou mais “Venda”.
* A entidade “Lote Processado” será responsável por armazenar os dados: ID, peso atual (kg), data de criação, tipo de material e o lote bruto. A entidade “Lote Processado” pode ter apenas 1 “Tipo de Material” e “Lote Bruto”. Já as entidades “Tipo de Material” e “Lote Bruto” podem ter 1 ou mais “Lote Processado”.
* A entidade “Etapa de Processamento” será responsável por armazenar os dados: lote processado, categoria de processamento, data e status de processamento. A entidade “Etapa de Processamento” pode ter apenas 1 “Lote Processado” e “Categoria de Processamento”. Já as entidades “Lote Processado” e “Categoria de Processamento” podem ter 1 ou mais “Etapa de Processamento”.
* A entidade “Item da Venda” será responsável por armazenar os dados: ID, tipo de material, venda, peso vendido (kg) e preço unitário por kg. A entidade “Item da Venda” pode ter apenas 1 “Tipo de Material” e “Venda”. Já as entidades “Tipo de Material” e “Venda” podem ter 1 ou mais “Item da Venda”.

### 4.1 Camadas e Padrões de Projeto

Este tópico apresenta a arquitetura do projeto, que segue o padrão MVC (*Model-View-Controller*), o que garante a separação clara entre a interface do usuário, a lógica de negócios e o acesso aos dados do sistema de Gestão de Cooperativa de Reciclagem. Abaixo, apresentamos a Figura 2, com a árvore de pacotes e pastas do projeto.

![Figura 2 - Árvore de pacotes e pastas do projeto implementada na IDE Eclipse](https://github.com/HigorPalmeira/gestao-cooperativa-reciclagem-web/blob/main/pacotes-projeto.png?raw=true)

#### 4.1.1 Modelo

A camada Modelo está representada no projeto nos pacotes/packages *model*, *dao*, *service* e o sub-pacote *model.enums*.

#### 4.1.2 Visão

A camada Visão está representada no projeto nos pacotes/packages *webapp* e nas sub-pastas *pages*.

### 4.1.3 Controlador

A camada Controlador está representada no projeto no pacote/package *controller*.

### 4.1.4 Outros Pacotes

Nesta seção, contém detalhes sobre outros pacotes presentes no projeto.

#### 4.1.4.1 Filtro

A camada Filtro está representada no projeto no pacote/package *filter*.

#### 4.1.4.2 Utilitários

A camada Utilitário está representada no projeto no pacote/package *utils*.

### 4.2 Telas

Este tópico apresenta o sistema de **Gestão de Cooperativa de Reciclagem**. As telas abaixo são apresentadas por ordem de usabilidade no sistema e são explicadas de forma detalhada após cada imagem.

## 5. Links

Esta seção contém o link do projeto disponibilizado no GitHub, para visualização da estrutura implementada.

Link: [gestao-cooperativa-reciclagem-web](https://github.com/HigorPalmeira/gestao-cooperativa-reciclagem-web).
