# webshop-backend

Backend for a webshop created in Spring Boot 2, its secured by Spring Security and has 2 different configurations, one for admins using form validation and thymeleaf and another for users using JSON web tokens.

The shop has some nice features like persisting an users cart across sessions, emailing a confirmation email and allowing admins to track users and orders.
When an order is placed the cart is transformed to a ShopOrder which contains a JSON-field with all the products, quantity and sum, this allows an entire order to be stored on one row instead of creating a new row for each product in an old order.

Frontend at https://github.com/Xenofono/react-native-shop-frontend
