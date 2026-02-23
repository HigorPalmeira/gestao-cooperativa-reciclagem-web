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
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    
</head>
<body>

    <!-- Navegação Global -->
    <nav class="main-nav">
        <div class="brand">ERP Reciclagem</div>
        <div class="nav-links">
            <!-- Funcionalidades Core -->
            <a href="${pageContext.request.contextPath}/ListarFornecedores">Fornecedores</a> <!-- pages/fornecedor/fornecedores.jsp -->
            <a href="${pageContext.request.contextPath}/ListarLotesBruto">Lotes Brutos</a>
            <a href="${pageContext.request.contextPath}/ListarLotesProcessados">Lotes Processados</a>
            <a href="${pageContext.request.contextPath}/ListarVendas">Vendas</a>
            <a href="${pageContext.request.contextPath}/ListarClientes">Clientes</a>
            <a href="${pageContext.request.contextPath}/ListarTransacoesCompra">Financeiro</a>
            
            <!-- Dropdown para Cadastros Básicos -->
            <div class="dropdown">
                <a href="#">Configurações &#9662;</a>
                <div class="dropdown-content">
                    <a href="${pageContext.request.contextPath}/ListarCategoriasProcessamento">Categorias de Processamento</a>
                    <a href="${pageContext.request.contextPath}/ListarTiposMateriais">Tipos de Materiais</a>
                    <a href="${pageContext.request.contextPath}/ListarPrecosMateriais">Preços de Materiais</a>
                    <a href="${pageContext.request.contextPath}/ListarUsuarios">Usuários (Admin)</a>
                </div>
            </div>
            
            <a href="${pageContext.request.contextPath}/pages/login/login.jsp" style="margin-left: 15px; color: #ffcccc;">Sair</a>
        </div>
    </nav>

    <main class="container">
        <h2 style="color: #444; margin-bottom: 20px;">Painel de Controle</h2>

        <!-- 1. Cartões de Indicadores (KPIs) -->
        <section class="kpi-grid">
            <!-- KPI 1 -->
            <div class="kpi-card kpi-blue" onclick="window.location.href='ListarLotesBruto'"> <!-- pages/lote_bruto/lotesBrutos.jsp -->
                <div class="kpi-title">Lotes Brutos Recebidos (Hoje)</div>
                <div class="kpi-value">12</div>
                <div class="kpi-desc">Total de 4.500 Kg</div>
            </div>

            <!-- KPI 2 -->
            <div class="kpi-card kpi-green" onclick="window.location.href='ListarVendas'">
                <div class="kpi-title">Vendas do Mês (R$)</div>
                <div class="kpi-value">R$ 45.200</div>
                <div class="kpi-desc">+15% vs mês anterior</div>
            </div>

            <!-- KPI 3 -->
            <div class="kpi-card kpi-yellow" onclick="window.location.href='ListarTransacoesCompra'">
                <div class="kpi-title">Pagamentos Pendentes</div>
                <div class="kpi-value">5</div>
                <div class="kpi-desc">Valor Total: R$ 3.150</div>
            </div>

            <!-- KPI 4 -->
            <div class="kpi-card kpi-info" onclick="window.location.href='ListarLotesProcessados'">
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
                    <a href="${pageContext.request.contextPath}/pages/lotes_bruto/novoLoteBruto.jsp" class="btn-action action-batch">
                        + Novo Lote Bruto
                    </a>
                    <a href="${pageContext.request.contextPath}/NovaVenda" class="btn-action action-sale">
                        + Nova Venda
                    </a>
                    <a href="${pageContext.request.contextPath}/pages/fornecedor/novoFornecedor.jsp" class="btn-action action-supplier">
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