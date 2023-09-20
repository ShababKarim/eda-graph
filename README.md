<a name="readme-top"></a>
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <h3 align="center">EDA Graph</h3>

  <p align="center">
    A service designed to make tracing events in distributed, reactive systems easier
    <br />
    <a href="https://github.com/ShababKarim/eda-graph"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/ShababKarim/eda-graph/issues">Report Bug</a>
    ·
    <a href="https://github.com/ShababKarim/eda-graph/issues">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

This project is inspired by the many challenges when building a distributed system, namely microservices, in a reactive manner. 
Non-p2p and asynchronous communication patterns lead to non-deterministic behaviors that are difficult to debug.
For event-driven applications with a messaging bus (Kafka), EDA Graph monitors the events being sent back and forth for
the topics that you specify. Simply define the list of brokers and topics to watch and let the service begin monitoring.
Deploy to your K8s cluster or run as a docker container using `docker-compose`.

The project is also bootstrapped with 2 sample microservices for demo purposes: `marketing-service` and `sales-service`.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With


* [![Next][Next.js]][Next-url]
* [![Tailwind][Tailwind]][Tailwind-url]
* [![Spring Boot][Spring-Boot]][Spring-url]
* [![Apache Kafka][Kafka]][Kafka-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

### Prerequisites

1. Install `Docker`
2. Ensure events are being sent to Kafka as json following this schema

{
    "name": "",
    "traceId": "",
    "timestamp": LocalDateTime,
    "payload": T
}

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- USAGE EXAMPLES -->
## Usage

* Clone the repository
* Check out the `release` branch
* Replace the placeholders in docker-compose.yml in project root
* Run `docker compose up -d`


<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* [Domain-Driven Design by Eric Evans](https://www.amazon.com/Domain-Driven-Design-Tackling-Complexity-Software/dp/0321125215)
* [Learning Domain-Driven Design by Vlad Khononov](https://www.amazon.com/Learning-Domain-Driven-Design-Aligning-Architecture/dp/1098100131)
* [What do you mean by Event-driven by Martin Fowler](https://martinfowler.com/articles/201701-event-driven.html)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/ShababKarim/eda-graph.svg?style=for-the-badge
[contributors-url]: https://github.com/ShababKarim/eda-graph/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/ShababKarim/eda-graph.svg?style=for-the-badge
[forks-url]: https://github.com/ShababKarim/eda-graph/network/members
[stars-shield]: https://img.shields.io/github/stars/ShababKarim/eda-graph.svg?style=for-the-badge
[stars-url]: https://github.com/ShababKarim/eda-graph/stargazers
[issues-shield]: https://img.shields.io/github/issues/ShababKarim/eda-graph.svg?style=for-the-badge
[issues-url]: https://github.com/ShababKarim/eda-graph/issues
[license-shield]: https://img.shields.io/github/license/ShababKarim/eda-graph.svg?style=for-the-badge
[license-url]: https://github.com/ShababKarim/eda-graph/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/shabab-karim-43262413b
[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white
[Next-url]: https://nextjs.org/
[Tailwind]: https://img.shields.io/badge/tailwindcss-%2338B2AC.svg?style=for-the-badge&logo=tailwind-css&logoColor=white
[Tailwind-url]: https://tailwindcss.com/
[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white
[Next-url]: https://nextjs.org/
[Spring-boot]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[Spring-url]: https://start.spring.io
[Kafka]: https://img.shields.io/badge/Apache%20Kafka-000?style=for-the-badge&logo=apachekafka
[Kafka-url]: https://kafka.apache.org/
