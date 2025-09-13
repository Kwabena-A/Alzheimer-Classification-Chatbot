

# 🧠 Alzheimer Prediction Chatbot

This is a **Java-based chatbot** that simulates the **Mini-Mental State Examination (MMSE)**, collects basic demographic and lifestyle features, and predicts whether a user is at risk of **Alzheimer’s Disease** using a pre-trained **Random Forest model**.

⚠️ **Disclaimer:** This project is for **educational purposes only**. It is **not** a medical tool and should not be used for real diagnosis. Always consult a licensed medical professional.

---

## Demo

https://github.com/user-attachments/assets/12784173-a5dd-48b9-a218-29b5c478d57d

## 🚀 Features
- **Interactive Chatbot** with typing effect and console UI.
- **MMSE Exam Simulation**:
    - Orientation (date, location).
    - Memory recall.
    - Attention & calculation (counting backwards).
    - Language (sentence formation).
- **Scoring System** → Outputs MMSE score out of 30.
- **User Information Collection**:
    - Age.
    - Gender.
    - Years of education.
    - Socio-Economic Status (SES).
- **Prediction**:
    - Uses a Random Forest model (exported from Python) for classification.
    - Votes across trees to return likelihood of being *Demented* or *Non-Demented*.

## Run
    cd (Project Directory e.g. C:\Users\Josh\Downloads\Alziheimers ChatBot>)
    Java Main.java

