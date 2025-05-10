<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавление услуги</title>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
    <style>
        /* Новые стили для поля цены */
        .price-input {
            position: relative;
        }
        .price-input input {
            padding-left: 20px;
        }
        .price-input::before {
            content: "₽";
            position: absolute;
            left: 10px;
            top: 50%;
            transform: translateY(-50%);
            font-weight: bold;
        }
        input[type="number"] {
            width: 200px;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
    </style>
    <script>
        $(function() {
            const masters = {
                "Косметология": [
                    "Иванов Иван Иванович",
                    "Петрова Полина Сергеевна",
                    "Соколова Елена Владимировна"
                ],
                "Массаж": [
                    "Сидоров Сергей Михайлович",
                    "Кузнецова Ксения Александровна",
                    "Васильев Артем Игоревич"
                ],
                "Маникюр": [
                    "Смирнова Светлана Дмитриевна",
                    "Федоров Федор Николаевич",
                    "Морозова Анна Викторовна"
                ],
                "Прически": [
                    "Ковалева Мария Олеговна",
                    "Николаев Денис Петрович",
                    "Зайцева Ольга Игоревна"
                ],
            };

            $("#type").change(function() {
                let type = $(this).val();
                let masterSelect = $("#fioMaster");
                masterSelect.empty();
                masterSelect.append('<option value="">Выберите мастера</option>');

                if(masters[type]) {
                    masters[type].forEach(function(master) {
                        masterSelect.append(new Option(master, master));
                    });
                }
            });

            // Валидация цены
            $("input[name='price']").on('input', function() {
                let value = $(this).val();
                value = value.replace(/[^\d.]/g, '');
                if ((value.match(/\./g) || []).length > 1) {
                    value = value.substring(0, value.lastIndexOf('.'));
                }
                $(this).val(value);
            });

            $("form").submit(function(e) {
                let isValid = true;
                let errorMessage = "";

                // Проверка полей
                $("input, select").each(function() {
                    if ($(this).is(":visible") && !$(this).val()) {
                        isValid = false;
                        errorMessage += "Поле '" + $(this).attr("name") + "' не может быть пустым.\n";
                    }
                });

                // Дополнительная проверка цены
                let price = $("input[name='price']").val();
                if (price && (isNaN(price) || parseFloat(price) <= 0)) {
                    isValid = false;
                    errorMessage += "Цена должна быть положительным числом.\n";
                }

                if (!isValid) {
                    e.preventDefault(); // Отменить отправку формы
                    alert(errorMessage);
                }
            });
        });
    </script>
</head>
<body>
<a href="menu_admin.jsp">Назад</a>
<h3>Добавление косметической услуги</h3>
<hr/>
<form name="addServiceForm" method="POST" action="controller">
    <input type="hidden" name="command" value="addService" />

    Название:<br/>
    <input type="text" name="name" value="" required> <br/><br/>

    Тип:<br/>
    <select id="type" name="type" required>
        <option value="">Выберите тип услуги</option>
        <option value="Косметология">Косметология</option>
        <option value="Массаж">Массаж</option>
        <option value="Маникюр">Маникюр</option>
        <option value="Прически">Парикмахерские услуги</option>
    </select><br/><br/>

    ФИО Мастера:<br/>
    <select id="fioMaster" name="fioMaster" required>
        <option value="">Сначала выберите тип услуги</option>
    </select><br/><br/>

    Время:<br/>
    <input type="datetime-local" id="datetime-local" name="time" value="" required> <br/><br/>

    Цена:<br/>
    <div class="price-input">
        <input type="text" name="price" value="" placeholder="0.00" required>
    </div><br/>

    <input type="submit" value="Добавить услугу">
</form>
<hr/>
</body>
</html>