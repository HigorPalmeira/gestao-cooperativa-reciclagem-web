<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Início - Gestão de Cooperativa</title>
    <!-- Importação do Chart.js para o gráfico -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        /* --- CSS: Estilização Visual (Padrão ERP) --- */
        :root {
            --primary-color: #0056b3;
            --secondary-color: #6c757d;
            --background-color: #f4f6f9;
            --white: #ffffff;
            --border-color: #dee2e6;
            --success-color: #28a745;
            --warning-color: #ffc107;
            --danger-color: #dc3545;
            --info-color: #17a2b8;
            --text-color: #333;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: var(--background-color);
            color: var(--text-color);
        }

        /* --- Menu de Navegação (Topo) --- */
        nav.main-nav {
            background-color: var(--primary-color);
            color: var(--white);
            padding: 0.8rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
        }

        nav.main-nav .brand { font-weight: bold; font-size: 1.3rem; margin-right: 20px;}
        
        .nav-links {
            display: flex;
            gap: 15px;
            flex-wrap: wrap;
            font-size: 0.9rem;
        }

        .nav-links a {
            color: #fff;
            text-decoration: none;
            opacity: 0.85;
            transition: opacity 0.2s;
            cursor: pointer;
        }
        .nav-links a:hover { opacity: 1; text-decoration: underline; }

        /* Dropdown simples para Configurações */
        .dropdown { position: relative; display: inline-block; }
        .dropdown-content {
            display: none;
            position: absolute;
            background-color: #f9f9f9;
            min-width: 160px;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            z-index: 1;
            border-radius: 4px;
        }
        .dropdown-content a {
            color: black;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
        }
        .dropdown-content a:hover {background-color: #f1f1f1;}
        .dropdown:hover .dropdown-content {display: block;}


        /* --- Layout do Dashboard --- */
        .container {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 1rem;
        }

        /* Seção 1: KPIs (Grid de 4 cartões) */
        .kpi-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
            gap: 1.5rem;
            margin-bottom: 2rem;
        }

        .kpi-card {
            background-color: var(--white);
            padding: 1.5rem;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            border-left: 5px solid var(--primary-color);
            cursor: pointer;
            transition: transform 0.2s, box-shadow 0.2s;
        }

        .kpi-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }

        .kpi-title { font-size: 0.9rem; color: #666; margin-bottom: 0.5rem; font-weight: 600; text-transform: uppercase;}
        .kpi-value { font-size: 1.8rem; font-weight: bold; color: #2c3e50; }
        .kpi-desc { font-size: 0.8rem; color: #888; margin-top: 5px; }

        /* Cores específicas para bordas dos KPIs */
        .kpi-blue { border-left-color: var(--primary-color); }
        .kpi-green { border-left-color: var(--success-color); }
        .kpi-yellow { border-left-color: var(--warning-color); }
        .kpi-info { border-left-color: var(--info-color); }

        /* Seção 2 e 3: Gráfico e Ações (Grid assimétrico) */
        .main-dashboard-area {
            display: grid;
            grid-template-columns: 2fr 1fr; /* 2 partes gráfico, 1 parte ações */
            gap: 1.5rem;
        }

        .dashboard-card {
            background-color: var(--white);
            padding: 1.5rem;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            border: 1px solid var(--border-color);
        }

        .card-header {
            margin-bottom: 1.5rem;
            border-bottom: 1px solid #eee;
            padding-bottom: 0.5rem;
            font-size: 1.1rem;
            font-weight: bold;
            color: #444;
        }

        /* Botões de Ação Rápida */
        .quick-actions-list {
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }

        .btn-action {
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 15px;
            border-radius: 6px;
            text-decoration: none;
            font-weight: bold;
            color: white;
            transition: opacity 0.2s;
            text-align: center;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .btn-action:hover { opacity: 0.9; }

        /* Cores dos botões */
        .action-batch { background-color: var(--primary-color); }
        .action-sale { background-color: var(--success-color); }
        .action-supplier { background-color: var(--info-color); }

        /* Responsividade */
        @media (max-width: 768px) {
            .main-dashboard-area {
                grid-template-columns: 1fr; /* Empilha em telas menores */
            }
            .nav-links {
                display: none; /* Em produção, usaríamos um menu hambúrguer */
            }
        }
    </style>
</head>
<body>

    <!-- Navegação Global -->
    <nav class="main-nav">
        <div class="brand">ERP Reciclagem</div>
        <div class="nav-links">
            <!-- Funcionalidades Core -->
            <a href="ListarFornecedores">Fornecedores</a> <!-- pages/fornecedor/fornecedores.jsp -->
            <a href="pages/lotes/lotesBrutos.jsp">Lotes Brutos</a>
            <a href="pages/lotes/lotesProcessados.jsp">Lotes Processados</a>
            <a href="pages/venda/vendas.jsp">Vendas</a>
            <a href="pages/venda/clientes.jsp">Clientes</a>
            <a href="pages/fornecedor/transacoesCompra.jsp">Financeiro</a>
            
            <!-- Dropdown para Cadastros Básicos -->
            <div class="dropdown">
                <a href="#">Configurações &#9662;</a>
                <div class="dropdown-content">
                    <a href="pages/lotes/categoriasProcessamento.jsp">Categorias de Processamento</a>
                    <a href="pages/lotes/tiposMaterial.jsp">Tipos de Materiais</a>
                    <a href="pages/fornecedor/precosMaterial.jsp">Preços de Materiais</a>
                    <a href="pages/usuario/usuarios.jsp">Usuários (Admin)</a>
                </div>
            </div>
            
            <a href="login/login.html" style="margin-left: 15px; color: #ffcccc;">Sair</a>
        </div>
    </nav>

    <main class="container">
        <h2 style="color: #444; margin-bottom: 20px;">Painel de Controlo</h2>

        <!-- 1. Cartões de Indicadores (KPIs) -->
        <section class="kpi-grid">
            <!-- KPI 1 -->
            <div class="kpi-card kpi-blue" onclick="window.location.href='pages/lotes/lotesBrutos.jsp'">
                <div class="kpi-title">Lotes Brutos Recebidos (Hoje)</div>
                <div class="kpi-value">12</div>
                <div class="kpi-desc">Total de 4.500 Kg</div>
            </div>

            <!-- KPI 2 -->
            <div class="kpi-card kpi-green" onclick="window.location.href='pages/venda/vendas.jsp'">
                <div class="kpi-title">Vendas do Mês (R$)</div>
                <div class="kpi-value">R$ 45.200</div>
                <div class="kpi-desc">+15% vs mês anterior</div>
            </div>

            <!-- KPI 3 -->
            <div class="kpi-card kpi-yellow" onclick="window.location.href='pages/fornecedor/transacoesCompra.jsp'">
                <div class="kpi-title">Pagamentos Pendentes</div>
                <div class="kpi-value">5</div>
                <div class="kpi-desc">Valor Total: R$ 3.150</div>
            </div>

            <!-- KPI 4 -->
            <div class="kpi-card kpi-info" onclick="window.location.href='pages/lotes/lotesProcessados.jsp'">
                <div class="kpi-title">Estoque Processado</div>
                <div class="kpi-value">1.200 Kg</div>
                <div class="kpi-desc">Pronto para venda</div>
            </div>
        </section>

        <section class="main-dashboard-area">
            
            <!-- 2. Gráfico de Atividades -->
            <div class="dashboard-card">
                <div class="card-header">Movimentações Recentes (Últimos 7 dias)</div>
                <div style="position: relative; height: 300px; width: 100%;">
                    <canvas id="movementsChart"></canvas>
                </div>
            </div>

            <!-- 3. Ações Rápidas -->
            <div class="dashboard-card">
                <div class="card-header">Ações Rápidas</div>
                <div class="quick-actions-list">
                    <a href="pages/lotes/novoLoteBruto.jsp" class="btn-action action-batch">
                        + Novo Lote Bruto
                    </a>
                    <a href="pages/venda/novaVenda.jsp" class="btn-action action-sale">
                        + Nova Venda
                    </a>
                    <a href="pages/fornecedor/novoFornecedor.jsp" class="btn-action action-supplier">
                        + Novo Fornecedor
                    </a>
                </div>
                
                <div style="margin-top: 2rem; padding-top: 1rem; border-top: 1px solid #eee;">
                    <p style="font-size: 0.85rem; color: #666;">
                        <strong>Status do Sistema:</strong> <span style="color: var(--success-color)">Online</span><br>
                        <strong>Última atualização:</strong> Hoje, 14:30
                    </p>
                </div>
            </div>

        </section>
    </main>

    <script>
        /* --- JavaScript: Inicialização do Gráfico --- */
        
        document.addEventListener('DOMContentLoaded', function() {
            const ctx = document.getElementById('movementsChart').getContext('2d');
            
            // Dados simulados para o gráfico
            const chartData = {
                labels: ['Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb', 'Dom'],
                datasets: [
                    {
                        label: 'Entrada (Kg)',
                        data: [500, 750, 400, 900, 600, 200, 100],
                        backgroundColor: 'rgba(0, 86, 179, 0.6)', // Azul
                        borderColor: 'rgba(0, 86, 179, 1)',
                        borderWidth: 1
                    },
                    {
                        label: 'Saída/Venda (Kg)',
                        data: [300, 400, 450, 200, 800, 150, 0],
                        backgroundColor: 'rgba(40, 167, 69, 0.6)', // Verde
                        borderColor: 'rgba(40, 167, 69, 1)',
                        borderWidth: 1
                    }
                ]
            };

            const movementsChart = new Chart(ctx, {
                type: 'bar',
                data: chartData,
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        y: {
                            beginAtZero: true,
                            title: {
                                display: true,
                                text: 'Peso (Kg)'
                            }
                        }
                    },
                    plugins: {
                        legend: {
                            position: 'bottom'
                        }
                    }
                }
            });
        });
    </script>
</body>
</html>