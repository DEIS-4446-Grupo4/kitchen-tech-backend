# Kitchen-tech-backend
Backend Development of Kitchen Tech

## API Endpoints

| Endpoint                                               | Método  | Controlador              | Función                                                                                   |
|--------------------------------------------------------|---------|--------------------------|-------------------------------------------------------------------------------------------|
| `/api/kitchentech/v1/auth/register-restaurant`         | `POST`  | `AuthController`         | Registra todos los restaurantes que trabajan en Kitchen Tech.                             |
| `/api/kitchentech/v1/auth/register-staff`              | `POST`  | `AuthController`         | Registra al staff que contienen los restaurantes.                                         |
| `/api/kitchentech/v1/auth/login`                       | `POST`  | `AuthController`         | Loguear un usuario, tanto empresa como staff.                                             |
| `/api/kitchentech/v1/restaurant`                       | `GET`   | `RestaurantController`   | Obtiene la lista de los restaurantes de Kitchen Tech.                                     |
| `/api/kitchentech/v1/restaurant/{restaurantId}`        | `GET`   | `RestaurantController`   | Obtener un restaurante mediante su ID.                                                    |
| `/api/kitchentech/v1/restaurant/{restaurantId}`        | `PUT`   | `RestaurantController`   | Actualiza un restaurante mediante su ID                                                   |
| `/api/kitchentech/v1/restaurant/{restaurantId}`        | `DELETE`| `RestaurantController`   | Elimina un restaurante mediante su ID.                                                    |
| `/api/kitchentech/v1/staff-user`                       | `POST`  | `StaffUserController`    | Crea un staff mediante la aplicación, no mediante el register.                            |
| `/api/kitchentech/v1/staff-user/{staffUserId}`         | `GET`   | `StaffUserController`    | Obtiene un Staff por ID.                                                                  |
| `/api/kitchentech/v1/staff-user/{staffUserId}`         | `PUT`   | `StaffUserController`    | Actualiza un Staff por ID.                                                                |
| `/api/kitchentech/v1/staff-user/{staffUserId}`         | `DELETE`| `StaffUserController`    | Elimina un Staff por ID.                                                                  |
| `/api/kitchentech/v1/account/restaurant/{restaurantId}`| `GET`   | `AccountController`      | Obtiene todas las cuentas según un restaurante.                                           |
| `/api/kitchentech/v1/account/{accountId}`              | `GET`   | `AccountController`      | Obtiene una cuenta por su ID.                                                             |    
| `/api/kitchentech/v1/account`                          | `POST`  | `AccountController`      | Crea una cuenta respecto a un restaurante.                                                |    
| `/api/kitchentech/v1/account/{accountId}`              | `PUT`   | `AccountController`      | Actualiza una cuenta específica mendiante su ID.                                          |
| `/api/kitchentech/v1/account/{accountId}`              | `DELETE`| `AccountController`      | Elimina una cuenta específica mediante su ID.                                             |
| `/api/kitchentech/v1/account/{accountId}/products`     | `POST`  | `AccountController`      | Añade productos a la cuenta.                                                              |
| `/api/kitchentech/v1/account/{accountId}/products/{productId}`   | `PUT`   | `AccountController`  | Actualiza productos en una cuenta.                                                  |
| `/api/kitchentech/v1/account/{accountId}/products/{productId}`   | `DELETE`| `AccountController`  | Elimina productos en una cuenta.                                                    |
| `/api/kitchentech/v1/client/restaurant/{restaurantId}`           | `GET`   | `ClientController`   | Obtener todos los clientes mediante un ID de restaurante.                           |
| `/api/kitchentech/v1/client/{clientId}`                          | `GET`   | `ClientController`   | Obtiene un cliente por ID.                                                          |
| `/api/kitchentech/v1/client`                                     | `POST`  | `ClientController`   | Crea un cliente.                                                                    |
| `/api/kitchentech/v1/client/{clientId}`                          | `PUT`   | `ClientController`   | Actualiza un cliente por ID.                                                        |
| `/api/kitchentech/v1/client/{clientId}`                          | `DELETE`| `ClientController`   | Elimina un cliente por ID.                                                          |
| `/api/kitchentech/v1/product/restaurant/{restaurantId}`          | `GET`   | `ProductController`  | Obtiene todos los productos pertenecientes a un restaurante.                        |
| `/api/kitchentech/v1/product/{productId}`                        | `GET`   | `ProductController`  | Obtiene un producto por ID.                                                         |
| `/api/kitchentech/v1/product`                                    | `POST`  | `ProductController`  | Crea un producto.                                                                   |
| `/api/kitchentech/v1/product/{productId}`                        | `PUT`   | `ProductController`  | Actualiza un producto por ID.                                                       |
| `/api/kitchentech/v1/product/{productId}`                        | `DELETE`| `ProductController`  | Elimina un producto por ID.                                                         |
| `/api/kitchentech/v1/supply/restaurant/{restaurantId}`           | `GET`   | `SupplyController`   | Obtiene todos los suplementos pertenecientes a un restaurante.                      |
| `/api/kitchentech/v1/supply/{supplyId}`                          | `GET`   | `SupplyController`   | Obtiene un suplemento por ID.                                                       |
| `/api/kitchentech/v1/supply`                                     | `POST`  | `SupplyController`   | Crea un suplemento.                                                                 |
| `/api/kitchentech/v1/supply/{supplyId}`                          | `PUT`   | `SupplyController`   | Actualiza un suplemento por ID.                                                     |
| `/api/kitchentech/v1/supply/{supplyId}`                          | `DELETE`| `SupplyController`   | Elimina un suplemento por ID.                                                       |





### Descripción

- **AuthController**: Gestiona las operaciones CRUD para la autenticación de usuarios.
- **RestaurantController**: Gestiona las operaciones CRUD para las peticiones de los restaurantes.
- **StaffUserController**: Gestiona las operaciones CRUD del staff del restaurante, como los meseros.
- **AccountController**: Gestiona las operaciones CRUD para las cuentas, incluyendo a los clientes, mesas y productos en sus operaciones.
- **ClientController**: Gestiona las operaciones CRUD para los clientes exclusivamente y su creación y relación con las cuentas.
- **ProductController**: Gestiona las operaciones CRUD para los productos, a su vez se pueden asignar a las cuentas como una lista de productos.
- **SupplyController**: Gestiona las operaciones CRUD para las suplementos, que son asignados a los productos y añadidos por Front End.
- **TableController**: Gestiona las operaciones CRUD para las mesas que son insertadas, manejadas por el restaurante que gestionarán los dispositivos IOT.

