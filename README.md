# 🔐 RSA Encryption System

## 📌 Overview
This repository contains an **RSA Encryption System** implemented in **Java**.  
It includes:
- **LCG (Linear Congruential Generator)** for random number generation.
- **Miller-Rabin Test** for primality testing.
- **Extended Euclidean Algorithm** for computing modular inverses.
- **RSA Key Generation** for generating public and private keys.
- **RSA Encryption & Decryption** functions.
- **Main method** to demonstrate encryption and decryption.

---

## 🚀 Features
- **Generates random prime numbers** using LCG and Miller-Rabin test.
- **Computes RSA key pairs** (Public & Private keys).
- **Encrypts and decrypts messages** using modular exponentiation.
- **Displays detailed logs** for debugging and understanding the process.

---

## 🔧 How It Works
### **1️⃣ Generating RSA Key Pairs**
- Uses **LCG** to generate **100 random numbers**.
- Checks for **primality** using the **Miller-Rabin Test**.
- Selects **two distinct prime numbers (p, q)**.
- Computes:
  - **n = p × q** (modulus)
  - **φ(n) = (p-1) × (q-1)** (Euler’s Totient Function)
  - **Public Key (n, e)** → `e = 65537` (common choice)
  - **Private Key (n, d)** → `d = e⁻¹ mod φ(n)` (using Extended Euclidean Algorithm).

### **2️⃣ Encryption**
- Converts **plaintext to numbers**.
- Uses **modular exponentiation** to compute ciphertext:
  ```
  C = M^e mod n
  ```

### **3️⃣ Decryption**
- Uses **modular exponentiation** to decrypt ciphertext:
  ```
  M = C^d mod n
  ```
- Converts numbers back into text.

---

## 🖥️ Running the Program
### **📌 Option 1: Compile and Run in Terminal**
1. **Compile the Java file:**
   ```sh
   javac RSA_Encryption_System.java
   ```
2. **Run the program:**
   ```sh
   java RSA_Encryption_System
   ```

### **📌 Option 2: Run in an IDE (IntelliJ, Eclipse, VS Code)**
1. Open the **RSA_Encryption_System.java** file in your IDE.
2. Run the `main()` method.

---

## 📜 Example Output
```
Hi there! This is the main method
calling generateKeys...
p is 61, q is 53
Calculated φ(n): 3120
Public Key: (n=3233, e=65537)
Private Key: (n=3233, d=2753)
Encrypting message: "Norah"
Encrypted Values: [XXXX, XXXX, XXXX, ...]
Decrypted Message: "Norah"
Decryption successful!
```

---

## 📝 Notes
- **This implementation is for educational purposes only.**
- **Not recommended for real-world cryptographic applications.**
- The **modular exponentiation** method ensures fast encryption and decryption.

---

## 📜 License
This project is for **educational purposes only**.  

