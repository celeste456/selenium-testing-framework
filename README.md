
# 🧪 Selenium UI Testing Framework (Selenium + TestNG + Allure)

This project is a **UI Test Automation Framework built from scratch** to showcase strong test automation practices for web applications.  
It uses **Java, Selenium WebDriver, TestNG, and Allure Reports**, and is integrated with **GitHub Actions** for continuous integration and reporting.

---

## 🚀 Project Features
- ✅ **Page Object Model (POM)** for clean and maintainable test structure.
- ✅ **Custom Test Listener** for automatic screenshot capture on success/failure and Allure integration.
- ✅ **Dynamic test data generation** using `Java Faker`.
- ✅ **Allure Reports** automatically generated and published via GitHub Actions.
- ✅ **Parallel execution ready** with TestNG.
- ✅ **Reusable DriverFactory** with Chrome options and `ThreadLocal` support.
- ✅ **Logging & Reporting**: Automatic attachment of screenshots and test data (JSON) to Allure reports.

---

## 🌐 Application Under Test
The framework is designed for **UI testing of the Tricentis Demo Web Shop**:  
[https://demowebshop.tricentis.com/](https://demowebshop.tricentis.com/)  

**Main Test Scenarios:**
- User Registration (valid, invalid data).
- User Login (valid credentials, invalid password, non-existent user).
- Shopping Cart (add, update, remove products).
- Checkout Process (guest checkout, returning customer checkout).

---

## 📂 Project Structure
```
src/
├── main/
│   └── java/
│       ├── drivers/             # DriverFactory (ThreadLocal WebDriver)
│       ├── model/               # Data models (UserData, CheckoutData)
│       ├── pages/               # Page Object Models
│       └── utils/               # FakerUtils, ConfigReader, AllureUtils
├── test/
│   └── java/
│       ├── base/                # BaseTest with WebDriver setup/teardown
│       ├── listeners/           # TestListener (screenshots + Allure attachments)
│       └── tests/               # TestNG test classes (LoginTest, RegisterTest, etc.)
│
└── resources/
    └── testng.xml               # TestNG suite configuration
```

---

## 🛠️ Tools & Technologies
- **Java 17**
- **Selenium**
- **TestNG**
- **Allure**
- **Maven**
- **WebDriverManager**
- **Java Faker**
- **GitHub Actions** (CI pipeline)

---

## 🧪 Running the Tests
**Run all tests with Maven:**
```bash
mvn clean test
```

**Generate Allure Report locally:**
```bash
mvn allure:report
mvn allure:serve
```

---

## 🤖 CI/CD with GitHub Actions
The framework includes a **CI pipeline** configured in `.github/workflows/ci.yml` that:
- Runs all tests on push.
- Generates the Allure report.
- Publishes the report to **GitHub Pages** at:
  **[https://celeste456.github.io/selenium-testing-framework/](https://celeste456.github.io/selenium-testing-framework/)**

---

## 📌 How to Extend
- Add more **Page Objects** and **Test Cases** for other modules.
- Integrate with **cloud providers** like BrowserStack or Selenium Grid.
- Add **cross-browser testing** support (Firefox, Edge).
- Parameterize test data and environments with **Maven profiles**.

---


## ⚡ Quick Start

Follow these steps to quickly set up and run the project locally:

### 1. Clone the Repository
```bash
git clone https://github.com/YOUR_USERNAME/selenium-testing-framework.git
cd selenium-testing-framework
```

### 2. Install Dependencies
Make sure you have **Java 17** and **Maven** installed. Then run:
```bash
mvn clean install
```

### 3. Run Tests
To run all tests with TestNG:
```bash
mvn clean test
```

### 4. View Allure Report
Generate and open the report locally:
```bash
mvn allure:serve
```

## 👨‍💻 Author
**Celeste May Herrera** – QA Automation Engineer  

---
