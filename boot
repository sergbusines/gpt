import telebot
from flask import Flask, request

# ⚙️ Замени на свой токен от BotFather
TOKEN = '7523833099:AAFUPKE8sF1Y-vBirrESMch3sAMQAlFYxAM'

# 🆔 Твой Telegram ID
ADMIN_ID = '1194428864'

# 🔗 Ссылки на документы
LINK_PRIVACY_POLICY = "https://docs.google.com/document/d/1J5Wa6GELBp27EFOHCx8fjnn_90RjYPmVf5Zan_Z95sk/edit?usp=drivesdk"
LINK_USER_AGREEMENT = "https://docs.google.com/forms/d/e/1FAIpQLSdkN91ZPVabu2GAFefsVW8UY-bDjJVGTliaxrbdLID8--tFCg/viewform?usp=header"
LINK_PUBLIC_OFFER = "https://docs.google.com/document/d/1HpFGUmRw57g2Q8ZXXGnvluDR5803Gi__38-5l-FUywo/edit?usp=drivesdk"

# 🔗 Google Форма "Стать партнером" 
PARTNER_FORM_LINK = https://docs.google.com/forms/d/e/1FAIpQLSfGsrT5takuihUqrmI4fs-U_uGyjrKCG9hvRnDjxx-eQum7BA/viewform?usp=sharing&ouid=104654855375289783360" 

# 🔗 Ссылка на шаблон
TEMPLATE_LINK = "https://drive.google.com/file/d/.../view?usp=sharing"

# 📊 Webhook для отправки данных в Google Таблицу 
GOOGLE_SHEET_WEBHOOK = "AKfycbw1p5GpOPPhxKn5jn1u8LQyBUSoksGMTKRRVyj7Bs5bl1Glm7E2L8aAahiGnIrPw_zB7Q" 

# 🧠 Инициализация бота
bot = telebot.TeleBot(TOKEN)

# 🌐 Flask сервер для webhook
app = Flask(__name__)

@app.route(f'/{TOKEN}', methods=['POST'])
def webhook():
    update = telebot.types.Update.de_json(request.stream.read().decode('utf-8'))
    bot.process_new_updates([update])
    return "OK", 200

# 📲 /start — главное меню
@bot.message_handler(commands=['start'])
def start(message):
    markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
    btn1 = types.KeyboardButton("Получить гайд")
    btn2 = types.KeyboardButton("Купить шаблон")
    btn3 = types.KeyboardButton("Стать партнером")
    btn4 = types.KeyboardButton("Юридические документы")
    markup.add(btn1, btn2, btn3, btn4)
    bot.send_message(message.chat.id,
                     "👋 Привет! Я помогу тебе начать делать видео с нуля.\n\nВыбери, что тебе нужно:",
                     reply_markup=markup)

# 📥 Обработчик текстовых команд (остальной функционал — как в предыдущей версии)
# ... (тут оставь тот же код, что был выше)

# 📤 Отправка данных в Google Таблицу
def send_analytics(name, tg, action):
    data = {"name": name, "tg": tg, "action": action}
    try:
        requests.post(GOOGLE_SHEET_WEBHOOK, json=data)
    except Exception as e:
        print(f"[Ошибка] Не удалось отправить данные в таблицу: {e}")

# ▶️ Запуск Flask-сервера
if __name__ == '__main__':
    app.run()
