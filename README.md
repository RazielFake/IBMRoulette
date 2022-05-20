
# Rest Roulette

Proyecto para la Academia de Microservicios de IBM el cual consiste en emular una ruleta.

El proyecto hace uso de Spring-Boot, Hibernate, JPA, Validation, Postgres, Lombok, entre otras. 

Como requerimientos mínimos el proyecto deberá tener los siguientes Endpoints.

> Ruleta
>> Endpoint para crear una ruleta el cual devolverá el Id de la ruleta creada.
>> 
>> Enpoint de apertura de ruleta, el cual permitirá las posteriores apuestas, este devuelve un estado de confirmación.
>> 
>> Endpoint de cierre de apuestas, este endpoint deberá devolver el resultado de las apuestas.
>> 
>> Endpoint listando las ruletas segun sus estados, abierta o cerrada.


> Apuestas
>> Endpoint para la creacion de apuestas por un mínimo de $100 y un máximo de $10,000. Las apuestas pueden realizarse ya sea por numero o por color: rojo, negro o verde.

Adicionalmente se creó una entidad extra, con el nombre de "Player" la cual permite agregar un jugador por nombre y asociar apuestas a dicho jugador.

