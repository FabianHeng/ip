# 🌸 Lili - Your Personal Task Tracker 🌸

![Ui.png](Ui.png)
Lili is a clean, simple, and garden-themed chatbot designed to help you keep track of your to-dos,
deadlines, and events. Inspired by the lily plant, Lili is here to keep your tasks organized
and blooming with productivity! 🌿🌸

## Features 🌱

- **Track your to-dos** 📝: Mark them done once you have completed them!
- **Set deadlines** ⏰
- **Organize events** 📅
- **Persistent memory** 🧠: Lili remembers your tasks even after you exit the chat, so you can come back anytime
and pick up where you left off! 🌟

## Commands 💬
Here’s a list of some commands you can use to interact with Lili. Each command is simple and intuitive,
designed to make task management a breeze. More commands can be shown by simply messaging Lili "help".

### LIST: `list`
Display all your current tasks, deadlines, and events. 🌻

---
### TODO: `todo <taskname>`
Add a new task to your to-do list! 🌸

Example: `todo Water the plants`

**Expected Output:**
```
Nice! I've added it to your list:
[T] [] Water the plants
Now you have 1 task(s) in your list.
```

---
### DEADLINE: `deadline <taskname> /by <yyyy-MM-dd HHmm>`
Set a deadline for your task. ⏳

Example: `deadline Submit project report /by 2025-02-20 2359`

**Expected Output:**
```
Nice! I've added it to your list:
[D] [] Submit project report (by: Feb 20 2025, 11:59 pm)
Now you have 1 task(s) in your list.
```

---
### EVENT: `event <taskname> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>`
Schedule an event with a start and end time. 🌷

Example: `event Team meeting /from 2025-02-17 0900 /to 2025-02-17 1030`

**Expected Output:**
```
Nice! I've added it to your list:
[E] [] Team meeting (from: Feb 17 2025, 9:00 am to: Feb 17 2025, 10:30 am)
Now you have 13 task(s) in your list.
```

---
## 🌷 Ready to Get Started?

Simply start chatting with Lili and begin adding your to-dos, deadlines, and events. 🌸
Lili will help you keep your garden of tasks organized and blooming! 🌿

**Happy Chatting!** 🌼