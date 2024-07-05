# E-commerce Application with Spring Boot, JWT Authentication, MongoDB and Redis

## Overview

This project is a Spring Boot application for an e-commerce platform, demonstrating the integration of MongoDB for data persistence, Spring Security for user authentication, and Redis for token blacklisting.

## Features

- **MongoDB Integration**: Uses MongoDB to store user information (including cart) and product details.
- **Spring Security**: Provides authentication and authorization mechanisms to secure user access and protect endpoints.
- **Redis for Token Blacklisting**: Manages tokens in Redis to enhance security by blacklisting tokens upon user logout.

## Requirements

- MongoDB
- Docker (to run the application in containers)

## Setup

### Running Locally

1. **Clone the repository**

   ```bash
   git clone https://github.com/dkalaitz/Spring-Boot-E-commerce-API.git
   cd Spring-Boot-E-commerce-API
   ```

2. **Configure MongoDB**

   - Ensure MongoDB is running either locally or update `application.properties` with your MongoDB connection details.

3. **Configure Redis**

   - Ensure Redis is installed and running locally or update `application.properties` with your Redis connection details.

4. **Accessing the Application**

   - Once the application is running, you can access it at `http://localhost:8080`.

### Running with Docker

1. **Clone the repository**

   ```bash
   git clone https://github.com/dkalaitz/Spring-Boot-E-commerce-API.git
   cd Spring-Boot-E-commerce-API
   ```

2. **Pull Docker Images**

   - Ensure you have Docker installed and running on your machine.
   - Pull the Docker images for the application, MongoDB, and Redis:

     ```bash
     dkalaitz/my-ecommerce-api
     docker pull mongo:latest
     docker pull redis:latest
     ```

3. **Run with Docker Compose**

   - Use Docker Compose to start the application along with MongoDB and Redis containers:

     ```bash
     docker-compose up
     ```

4. **Accessing the Application**

   - Once the containers are up and running, you can access the application at `http://localhost:8080`.

## Usage

- **Authentication**: Use Spring Security to authenticate users and authorize access to protected endpoints.
- **Product Management**: Implement endpoints to manage products such as getting all products, search by name, search by type.
- **User Management**: Handle user registration, login, and cart management.

## API Endpoints

- **Authentication Endpoints**:
  - `/api/auth/signup`: POST request for user registration.
     ```bash
     {
          "username": "exampleUsername",
          "email": "example123@example.com",
          "password": "example123", 
          "fullName": "ExampleFirstName ExampleLastName"
      }
     ```
  - `/api/auth/authenticate`: POST request to authenticate users and obtain access tokens.
    ```bash
     {
          "username": "exampleUsername",
          "password": "example123",
      }
     ```
  **Note:** After this request, a JWT Token is generated for future authorization.
  - `/api/auth/logout`: POST request to invalidate tokens and logout users. JWT Token is required **(Auth Type = Bearer Token)**.

- **Secure Endpoints**: Document endpoints that require authentication and authorization. JWT Token is required **(Auth Type = Bearer Token)**
  - GET `/api/users/myProfile`: Returns user's profile details.
  - GET `/api/users/myCart`: Returns user's cart.
  - POST `api/users/addToCart`: Adds a product to user's cart. ProductId and quantity are required.
  - POST `api/users/removeFromCart`: Removes a product from user's cart. ProductId is required.
  - POST `api/users/reduceQuantity`: Reduce quantity of a product that is in user's cart. ProductId is required.

- **Accessible Endpoints without Authentication**
  - GET `/api/products/getTypeProducts`: Returns a specific type list of products. Type is a required parameter.
  - GET `api/products/searchProduct`: Returns products that similar have similar name with a search term. A search term parameter is required.
  - GET `/api/products/getAllProducts`: Returns all products.
  - GET `/api/products/getProduct`: Returns a product based on id. ProductId parameter is required.
  - POST `/api/products/addProduct`: Adds a product.
      ```bash
      {
        "name" : "productName"
        "type" : "SmartPhone"
        "price" : 199.99
        "description" : "Product's Description"
        "imgURL" : "https://example.com/images/picture.jpg"
      }
      ```
  - POST `/api/products/deleteProduct`: Deletes a product. ProductId parameter is required.

