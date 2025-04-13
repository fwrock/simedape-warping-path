# SimEDaPE - Warping Path Calculation

🚀 **SimEDaPE - Warping Path Calculation** is a high-performance implementation of the **Simulation Estimation by Data Patterns Exploration (SimEDaPE)** technique using the **actor model** with the **Akka framework**, written in **Java 21**. This version was designed to significantly reduce the execution time of SimEDaPE’s most time-consuming step: the *Warping Path* calculation using Dynamic Time Warping (DTW).

## 📋 Overview

The growing demand for smart city simulations imposes performance challenges. **SimEDaPE** aims to accelerate simulations by estimating results based on previously observed patterns. However, previous implementations (in Python using Joblib and Cython) faced bottlenecks when handling large-scale data.

This library adopts the **actor model via Akka**, where each actor represents a processing unit responsible for computing the similarity between time series. The model provides isolation, fault tolerance, and efficient concurrency, yielding remarkable performance gains.

## 💡 Key Features

- 🧠 Optimized **Warping Path** calculation with DTW.
- ⚙️ Asynchronous actor-based model via **Akka Framework**.
- 🧵 Utilizes **Java 21 Virtual Threads** for efficient multithreading.
- 🔥 Up to **48× faster** than previous approaches.
- 🗃️ Supports massive datasets (tested with up to 6 million time series).
- 📦 Based on a highly optimized **FastDTW** implementation.

## 🧪 Experimental Results

| Dataset   | Time Series Count | Length | Clusters | Previous Time | SimEDaPE-Akka Time | Speedup |
|-----------|-------------------|--------|----------|----------------|---------------------|---------|
| Dataset 1 | 445,501           | 3,403  | 64       | 3,731.65s       | **76.8s**           | ~48×    |
| Dataset 2 | 6,000,000         | 6,806  | 64       | N/A (out of memory) | ✅             | —       |

> Benchmarked on a machine with **AMD EPYC 7453 (28 cores/56 threads)** and **1TB RAM**.

## 🏗️ Architecture

```plaintext
+-------------------+
| Managing Actor    | <-- coordinates execution
+--------+----------+
         |
         v
+--------+----------+
| Cluster Actor(s)  | <-- one per cluster
+--------+----------+
         |
         v
+-------------------+
| WarpingPathActor  | <-- calculates DTW between series
+-------------------+
```

## 🔧 Requirements
- Java 21+
- Maven or Gradle
- (Optional) IntelliJ IDEA or another IDE

### 📦 Installation

```bash
git clone https://github.com/your-user/simedape-akka.git
cd simedape-akka
./gradlew build
```

### 🚀 Running

```bash
./gradlew run
```

## 📝 Citation

```plaintext
ROCHA, Francisco Wallison; FRANCESQUINI, Emilio; CORDEIRO, Daniel. Improving Performance Estimation of Smart City Simulations Using the Actor Model. In: Escola Regional de Alto Desempenho de São Paulo (ERAD-SP). SBC, 2024. p. 85-88.
```

```plaintext
@inproceedings{rocha2024improving,
  title={Improving Performance Estimation of Smart City Simulations Using the Actor Model},
  author={Rocha, Francisco Wallison and Francesquini, Emilio and Cordeiro, Daniel},
  booktitle={Escola Regional de Alto Desempenho de S{\~a}o Paulo (ERAD-SP)},
  pages={85--88},
  year={2024},
  organization={SBC}
}
```


## 📜 License

This project is licensed under the terms of the Apache 2.0 License – see the LICENSE file for details.


