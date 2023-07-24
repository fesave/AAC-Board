# AAC-Board
[![Android CI](https://github.com/fesave/AAC-Board/actions/workflows/android_ci.yml/badge.svg)](https://github.com/fesave/AAC-Board/actions/workflows/android_ci.yml)


# Spanish Version :es:

## ¿Qué es AAC-Board?

AAC-Board es una aplicación Android creada por Javier Montaner ([@jmgjmg](https://github.com/jmgjmg)), Fernando Sanz ([@fesave](https://github.com/fesave)) y Carlos Jiménez ([@cjimenezsanchez](https://github.com/cjimenezsanchez)) durante el desarrollo de la formación [Architect Coders](https://architectcoders.com) impartida por el instructor Antonio Leiva ([@antoniolg](https://github.com/antoniolg)).

El objetivo de la aplicación AAC-Board, que hemos desarrollado conjuntamente, es dotar a aquellas personas, ya sean niños, jóvenes, adultos o ancianos, que por cualquier causa no han adquirido o han perdido un nivel de habla suficiente para comunicarse de forma satisfactoria, de una herramienta para poder usar un SAAC (Sistemas Aumentativos y Alternativos de Comunicación).

Para esta finalidad, nos hemos ayudado de la API que dispone el Centro Aragonés para la Comunicación Aumentativa y Alternativa (ARASAAC) para obtener los diversos pictogramas y facilitar el uso del SAAC.

## ¿Cómo hemos desarrollado el proyecto?

Para el desarrollo del proyecto, hemos optado por una arquitectura **MVVM**, tal y como recomiendan desde el equipo de desarrollo Android de Google. En este caso, hemos creado los siguientes submódulos:

### App:

En este módulo, hemos incluido todo lo referente a:

1. **Base de datos:** En nuestro caso, hemos utilizado **ROOM**, por lo que hemos incluido las clases *DAO* y *Entities*.
2. **Datasources:** Implementación de las interfaces *remotas* y *locales* que se han utilizado para obtener los pictogramas de la API de **ARASAAC**, obtener el idioma de hacer la consulta a la API en función de la ubicación del usuario y guardar los tableros en la base de datos.
3. **Inyección de dependencias:** En este caso, hemos usado **KOIN**.
4. **Respuestas de API:** Clases que modelan la respuesta de la API.
5. **Conexión con la API:** Usando **Retrofit**.
6. **UI/UX:** Todo lo referente al diseño y usabilidad de la App, usando una **SingleActivity** ayudándonos del *Navigation Component* al resto de *Fragments* y sus *ViewModels*.

### Data:

En este módulo, hemos incluido todo lo referente a los datos:

1. **Datasources:** Definición de las interfaces *remotas* y *locales*.
2. **Repositorio:** Implementación de las interfaces de los *repositorios*.

### Domain:

En este módulo, hemos incluido todo lo referente a la lógica de negocio:

1. **Data:** Clases de negocio correspondientes a las celdas y los tableros.
2. **Errores:** Definición de los errores que puede haber en la aplicación.
3. **Repositorio:** Definición de los repositorios.
4. **Casos de Uso:** Definición de los casos de uso que se implementarán en los *ViewModels*.

### AppTestShare:

Es un módulo añadido para ayudarnos a la hora de hacer tests en la aplicación.

## Funcionamiento de la App:

En el siguiente video se puede ver cómo es el funcionamiento de la App, siguiendo los siguientes pasos:

1. **Creación de tablero:** Al iniciar la aplicación por primera vez, no hay un tablero seleccionado por defecto y el listado de tableros está vacío. Por ello, es necesario crear uno nuevo. Para ello, haciendo clic en la *Toolbar*, accederemos al listado vacío. Después, clicamos en el botón de añadir para crear un nuevo tablero al cual le asignaremos un nombre, un número de filas y columnas, y por último, un icono distintivo para el listado.

2. **Edición del tablero:** Una vez creado el tablero, entraremos al modo **edición**, donde nos tocará asignar un valor a las diferentes casillas del mismo para poder utilizarlo más tarde. Haciendo clic en cada casilla, podremos editar su texto y su pictografía (fundamental para la aplicación). Desde aquí también podemos eliminar el tablero.

3. **Uso del tablero:** Al ser el primer tablero que creamos, si volvemos al listado, veremos que se ha marcado con una estrella, indicando que es el seleccionado por defecto y el que aparecerá la próxima vez que iniciemos la aplicación. Si seleccionamos las diferentes casillas, veremos cómo se va rellenando la barra superior, donde podremos rectificar en caso de confundirnos o eliminar todo. Pero si clicamos en el icono de hablar, la aplicación **pronunciará** los textos de cada casilla para que el usuario pueda comunicarse.



## License

```
Copyright 2023 - Carlos Jimenez, Javier Montaner and Fernando Sanz.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```