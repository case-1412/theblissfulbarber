<%@ page import="java.time.LocalDate, java.time.temporal.WeekFields, java.util.Locale, java.util.List" %>
<%@ page import="com.example.dao.AppointmentDao, com.example.model.Appointment" %>
<%
    // Fetch all appointments
    List<Appointment> list = AppointmentDao.getAllAppointments();

    double totalDaily = 0;
    double totalWeekly = 0;
    double totalMonthly = 0;
    double totalYearly = 0;

    LocalDate today = LocalDate.now();
    WeekFields weekFields = WeekFields.of(Locale.getDefault());
    int currentWeek = today.get(weekFields.weekOfWeekBasedYear());
    int currentMonth = today.getMonthValue();
    int currentYear = today.getYear();

    for (Appointment a : list) {
        try {
            LocalDate date = a.getDate();
            if (date == null) continue;

            if ("Complete".equalsIgnoreCase(a.getStatus())) {
                double price = a.getPrice() != null ? a.getPrice().doubleValue() : 0;

                if (date.isEqual(today)) {
                    totalDaily += price;
                }
                if (date.get(weekFields.weekOfWeekBasedYear()) == currentWeek &&
                    date.getYear() == currentYear) {
                    totalWeekly += price;
                }
                if (date.getMonthValue() == currentMonth &&
                    date.getYear() == currentYear) {
                    totalMonthly += price;
                }
                if (date.getYear() == currentYear) {
                    totalYearly += price;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Financial Statistics</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600&display=swap" rel="stylesheet" />
    <style>
        * {
            box-sizing: border-box;
        }
        body {
            font-family: 'Montserrat', sans-serif;
            background: #f0f2f5;
            margin: 0;
            padding: 0;
            color: #333;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        header {
            background-color: #34495e;
            width: 100%;
            padding: 20px 0;
            text-align: center;
            color: #ecf0f1;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }
        header h1 {
            margin: 0;
            font-weight: 600;
            font-size: 1.8rem;
            letter-spacing: 1px;
        }
        main {
            max-width: 1100px;
            width: 95%;
            margin: 40px auto 60px;
        }
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit,minmax(250px,1fr));
            gap: 25px;
            margin-bottom: 50px;
        }
        .card {
            background: white;
            border-radius: 15px;
            padding: 30px 25px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.07);
            text-align: center;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            cursor: default;
            position: relative;
        }
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 14px 28px rgba(0,0,0,0.12);
        }
        .card h3 {
            margin: 0 0 15px;
            font-size: 1.25rem;
            color: #2980b9;
            letter-spacing: 0.05em;
            font-weight: 700;
        }
        .card .amount {
            font-size: 2.8rem;
            font-weight: 700;
            color: #2c3e50;
            margin-bottom: 8px;
        }
        .card .period {
            font-size: 0.95rem;
            color: #7f8c8d;
        }
        #chartContainer {
            background: white;
            border-radius: 15px;
            padding: 25px 40px 40px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.07);
            max-width: 900px;
            margin: 0 auto 40px;
        }
        #revenueChart {
            max-height: 400px;
        }
        .back-btn {
            display: inline-block;
            background: #2980b9;
            color: white;
            padding: 14px 28px;
            border-radius: 8px;
            font-weight: 600;
            font-size: 1rem;
            text-decoration: none;
            transition: background 0.25s ease;
            box-shadow: 0 6px 12px rgba(41,128,185,0.3);
        }
        .back-btn:hover {
            background: #1c5980;
            box-shadow: 0 8px 16px rgba(28,89,128,0.5);
        }
        footer {
            width: 100%;
            text-align: center;
            padding: 15px 0;
            color: #95a5a6;
            font-size: 0.9rem;
            border-top: 1px solid #ddd;
            margin-top: auto;
        }
        @media (max-width: 450px) {
            .card .amount {
                font-size: 2rem;
            }
            .card h3 {
                font-size: 1.1rem;
            }
        }
    </style>
</head>
<body>

<header>
    <h1>Financial Statistics - <%= today %></h1>
</header>

<main>
    <section class="stats-grid">
        <div class="card">
            <h3>Daily Revenue</h3>
            <div class="amount">RM <%= String.format("%.2f", totalDaily) %></div>
            <div class="period"><%= today %></div>
        </div>
        <div class="card">
            <h3>Weekly Revenue</h3>
            <div class="amount">RM <%= String.format("%.2f", totalWeekly) %></div>
            <div class="period">Week <%= currentWeek %>, <%= currentYear %></div>
        </div>
        <div class="card">
            <h3>Monthly Revenue</h3>
            <div class="amount">RM <%= String.format("%.2f", totalMonthly) %></div>
            <div class="period">Month <%= currentMonth %>, <%= currentYear %></div>
        </div>
        <div class="card">
            <h3>Yearly Revenue</h3>
            <div class="amount">RM <%= String.format("%.2f", totalYearly) %></div>
            <div class="period"><%= currentYear %></div>
        </div>
    </section>

    <section id="chartContainer">
        <canvas id="revenueChart"></canvas>
    </section>

    <div style="text-align:center;">
        <a href="<%= request.getContextPath() %>/viewAppointments" class="back-btn" aria-label="Back to Appointments">? Back to Appointments</a>
    </div>
</main>

<footer>
    &copy; <%= currentYear %> Your Company Name. All rights reserved.
</footer>

<script>
    const ctx = document.getElementById('revenueChart').getContext('2d');
    new Chart(ctx, {
      type: 'bar',
      data: {
        labels: ['Daily', 'Weekly', 'Monthly', 'Yearly'],
        datasets: [{
          label: 'Revenue (RM)',
          data: [<%= totalDaily %>, <%= totalWeekly %>, <%= totalMonthly %>, <%= totalYearly %>],
          backgroundColor: ['#2980b9', '#27ae60', '#f39c12', '#c0392b'],
          borderRadius: 5,
          borderSkipped: false,
        }]
      },
      options: {
        responsive: true,
        plugins: {
            legend: { display: false },
            tooltip: {
                callbacks: {
                    label: context => `RM ${context.parsed.y.toFixed(2)}`
                }
            }
        },
        scales: {
          y: {
            beginAtZero: true,
            grid: { color: '#ecf0f1' },
            ticks: {
                color: '#7f8c8d',
                font: { size: 13 }
            }
          },
          x: {
            grid: { display: false },
            ticks: {
                color: '#34495e',
                font: { size: 14, weight: '600' }
            }
          }
        }
      }
    });
</script>

</body>
</html>
