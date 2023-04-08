 # Api-Paniers
 Ceci est le répertoire de l'API Paniers réalisé pour le R4.01 Architecture Logicielle fait par Ceccarelli Luca, en collaboration avec Egenscheviller Frédéric qui as fait l'Api Produits et Utilisateurs, et Saadi Nils qui a fait l'IHM.
 
 # Diagramme de cas d'utilisation
![Cas Utilisation](CasUtilisationAPIPanier.png "Diagramme de cas d'utilisation")
 
 # Fonctionnalités implementées
 ```bash
 //Basket
 //All baskets
 curl -H "Authorization: Bearer token" http://localhost:8080/API-Paniers-1.0-SNAPSHOT/api/baskets
 //Get basket
 curl -H "Authorization: Bearer token" http://localhost:8080/API-Paniers-1.0-SNAPSHOT/api/baskets/1/john
 //Add
 curl -H "Authorization: Bearer token" -H "Content-Type: application/json" -X POST -d '{"basket_id": 1, "confirmation_date":"2023-04-07", "confirmed":false, "username":"john"}' http://localhost:8080/API-Paniers-1.0-SNAPSHOT/api/baskets
 //Delete
 curl -H "Authorization: Bearer token" -X DELETE http://localhost:8080/API-Paniers-1.0-SNAPSHOT/api/baskets/1/john
 //Update
 curl -H "Authorization: Bearer token" -H "Content-Type: application/json" -X PUT -d '{"basket_id": 1, "confirmation_date":"2023-04-07", "confirmed":true, "username":"john"}' http://localhost:8080/API-Paniers-1.0-SNAPSHOT/api/baskets/1/john
 //Get all baskets of a user
 curl -H "Authorization: Bearer token" http://localhost:8080/API-Paniers-1.0-SNAPSHOT/api/baskets/john

//Content
//All
curl -H "Authorization: Bearer token" -X GET http://localhost:8080/API-Paniers-1.0-SNAPSHOT/api/contents
//Get
curl -H "Authorization: Bearer token" -X GET http://localhost:8080/API-Paniers-1.0-SNAPSHOT/api/contents/1/product1
//Add
curl -H "Authorization: Bearer token" -H "Content-Type: application/json" -X POST -d '{"basket_id":1,"product_name":"product1","quantity":10}' http://localhost:8080/API-Paniers-1.0-SNAPSHOT/api/contents
//Delete
curl -H "Authorization: Bearer token" -X DELETE http://localhost:8080/API-Paniers-1.0-SNAPSHOT/api/contents/1/product1
//Update
curl -H "Authorization: Bearer token" -H "Content-Type: application/json" -X PUT -d '{"basket_id":1,"product_name":"product1","quantity":20}' http://localhost:8080/API-Paniers-1.0-SNAPSHOT/api/contents/1/product1
```
 
 # Diagramme de classe
 
 ```mermaid
classDiagram
direction BT
class AuthenticationFilter {
  + AuthenticationFilter() 
  + filter(ContainerRequestContext) void
  - validateToken(String) void
}
class Basket {
  + Basket(int, Date, boolean, String) 
  + Basket() 
  - int basket_id
  - Date confirmation_date
  - String username
  - boolean confirmed
  + setUsername(String) void
  + setBasket_id(int) void
  + getConfirmation_date() Date
  + setConfirmation_date(Date) void
  + getUsername() String
  + setConfirmed(boolean) void
  + isConfirmed() boolean
  + getBasket_id() int
}
class BasketManagementApplication {
  + BasketManagementApplication() 
  - connectUserApi() UserRepositoryInterface
  - closeContentDbConnection(ContentManagementRepositoryInterface) void
  - openBasketDbConnection() BasketManagementRepositoryInterface
  - closeBasketDbConnection(BasketManagementRepositoryInterface) void
  - openContentDbConnection() ContentManagementRepositoryInterface
  + getSingletons() Set~Object~
  - connectProductApi() ProductRepositoryInterface
}
class BasketManagementRepositoryInterface {
<<Interface>>
  + close() void
  + addBasket(Basket) void
  + getBasket(int, String) Basket
  + getBasketsByUsername(String) ArrayList~Basket~
  + updateBasket(int, String, Date, boolean) boolean
  + getAllBaskets() ArrayList~Basket~
  + deleteBasket(int, String) void
}
class BasketManagementRepositoryMariadb {
  + BasketManagementRepositoryMariadb(String, String, String) 
  # Connection dbConnection
  + deleteBasket(int, String) void
  + close() void
  + updateBasket(int, String, Date, boolean) boolean
  + addBasket(Basket) void
  + getBasket(int, String) Basket
  + getBasketsByUsername(String) ArrayList~Basket~
  + getAllBaskets() ArrayList~Basket~
}
class BasketResource {
  + BasketResource(BasketService) 
  + BasketResource(BasketManagementRepositoryInterface, UserRepositoryInterface) 
  + BasketResource() 
  - BasketService service
  + updateBasket(int, String, String) Response
  + getAllBaskets() String
  + addBasket(String) Response
  + deleteBasket(int, String) Response
  + getBasketsByUsername(String) String
  + getBasket(int, String) String
}
class BasketService {
  + BasketService(BasketManagementRepositoryInterface, UserRepositoryInterface) 
  # BasketManagementRepositoryInterface basketRepo
  # UserRepositoryInterface userRepo
  + getAllBasketsJSON() String
  + getBasketsByUsernameJSON(String) String
  + deleteBasket(int, String) void
  + updateBasket(int, String, Basket) boolean
  + getBasketJSON(int, String) String
  + addBasket(Basket) void
}
class Content {
  + Content(int, String, int) 
  + Content() 
  - int basket_id
  - int quantity
  - String product_name
  + getBasket_id() int
  + getProduct_name() String
  + setBasket_id(int) void
  + setProduct_name(String) void
  + getQuantity() int
  + setQuantity(int) void
}
class ContentManagementRepositoryInterface {
<<Interface>>
  + getContent(int, String) Content
  + deleteContent(int, String) void
  + close() void
  + addContent(Content) void
  + getAllContents() ArrayList~Content~
  + updateContent(int, String, int) boolean
}
class ContentManagementRepositoryMariadb {
  + ContentManagementRepositoryMariadb(String, String, String) 
  # Connection dbConnection
  + updateContent(int, String, int) boolean
  + deleteContent(int, String) void
  + getContent(int, String) Content
  + close() void
  + addContent(Content) void
  + getAllContents() ArrayList~Content~
}
class ContentResource {
  + ContentResource() 
  + ContentResource(ContentService) 
  + ContentResource(ContentManagementRepositoryInterface, ProductRepositoryInterface) 
  - ContentService service
  + deleteContent(int, String) Response
  + getAllContents() String
  + addContent(String) Response
  + updateContent(int, String, String) Response
  + getContent(int, String) String
}
class ContentService {
  + ContentService(ContentManagementRepositoryInterface, ProductRepositoryInterface) 
  # ContentManagementRepositoryInterface contentRepo
  # ProductRepositoryInterface productRepo
  + getAllContentsJSON() String
  + updateContent(int, String, Content) boolean
  + addContent(Content) void
  + deleteContent(int, String) void
  + getContentJSON(int, String) String
}
class Product {
  + Product(String, int, float, String) 
  + Product() 
  # int quantity_stock
  # float price
  # String unit
  # String name
  + setName(String) void
  + setUnit(String) void
  + getQuantity_stock() int
  + setPrice(float) void
  + getUnit() String
  + getName() String
  + setQuantity_stock(int) void
  + getPrice() float
}
class ProductRepositoryApi {
  + ProductRepositoryApi(String) 
  ~ String url
  + updateProduct(String, Product) boolean
  + getProduct(String) Product
  + close() void
}
class ProductRepositoryInterface {
<<Interface>>
  + close() void
  + getProduct(String) Product
  + updateProduct(String, Product) boolean
}
class User {
  + User() 
  + User(String, String, String, String, String, String) 
  # String username
  # String password
  # String firstname
  # String mail
  # String lastname
  # String role
  + getLastname() String
  + getMail() String
  + setUsername(String) void
  + getFirstname() String
  + setFirstname(String) void
  + setLastname(String) void
  + getRole() String
  + setRole(String) void
  + setPassword(String) void
  + getUsername() String
  + setMail(String) void
  + getPassword() String
}
class UserRepositoryApi {
  + UserRepositoryApi(String) 
  ~ String url
  + close() void
  + getUser(String) User
}
class UserRepositoryInterface {
<<Interface>>
  + close() void
  + getUser(String) User
}

BasketManagementApplication  ..>  AuthenticationFilter : «create»
BasketManagementApplication  ..>  BasketManagementRepositoryMariadb : «create»
BasketManagementApplication  ..>  BasketResource : «create»
BasketManagementApplication  ..>  BasketService : «create»
BasketManagementApplication  ..>  ContentManagementRepositoryMariadb : «create»
BasketManagementApplication  ..>  ContentResource : «create»
BasketManagementApplication  ..>  ContentService : «create»
BasketManagementApplication  ..>  ProductRepositoryApi : «create»
BasketManagementApplication  ..>  UserRepositoryApi : «create»
BasketManagementRepositoryMariadb  ..>  Basket : «create»
BasketManagementRepositoryMariadb  ..>  BasketManagementRepositoryInterface 
BasketResource  ..>  BasketService : «create»
BasketResource "1" *--> "service 1" BasketService 
BasketService "1" *--> "basketRepo 1" BasketManagementRepositoryInterface 
BasketService "1" *--> "userRepo 1" UserRepositoryInterface 
ContentManagementRepositoryMariadb  ..>  Content : «create»
ContentManagementRepositoryMariadb  ..>  ContentManagementRepositoryInterface 
ContentResource  ..>  ContentService : «create»
ContentResource "1" *--> "service 1" ContentService 
ContentService "1" *--> "contentRepo 1" ContentManagementRepositoryInterface 
ContentService "1" *--> "productRepo 1" ProductRepositoryInterface 
ProductRepositoryApi  ..>  ProductRepositoryInterface 
UserRepositoryApi  ..>  UserRepositoryInterface 
```
