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

<p align="center">
<img src="https://github.com/fesave/AAC-Board/blob/develop/media/example_aac.gif" width="250" />
</p>

# English Version :uk: / :us:

## What is AAC-Board?

AAC-Board is an Android application created by Javier Montaner ([@jmgjmg](https://github.com/jmgjmg)), Fernando Sanz ([@fesave](https://github.com/fesave)), and Carlos Jiménez ([@cjimenezsanchez](https://github.com/cjimenezsanchez)) during the development of the training program [Architect Coders](https://architectcoders.com) taught by instructor Antonio Leiva ([@antoniolg](https://github.com/antoniolg)).

The objective of the AAC-Board application, which we have jointly developed, is to provide a tool for those people, whether they are children, young people, adults, or the elderly, who, for any reason, have not acquired or have lost a sufficient level of speech to communicate effectively. This tool allows them to use an AAC (Augmentative and Alternative Communication) system.

For this purpose, we have used the API provided by the Aragonese Center for Augmentative and Alternative Communication (ARASAAC) to obtain various pictograms, facilitating the use of the AAC system.

## How did we develop the project?

For the development of the project, we opted for an **MVVM** architecture, as recommended by the Android development team at Google. In this case, we have created the following submodules:

### App:

In this module, we have included everything related to:

1. **Database:** In our case, we have used **ROOM**, so we have included the *DAO* and *Entities* classes.
2. **Datasources:** Implementation of the *remote* and *local* interfaces used to obtain the pictograms from the **ARASAAC** API, get the user's location-based language, and save the boards in the database.
3. **Dependency Injection:** In this case, we have used **KOIN**.
4. **API Responses:** Classes that model the API response.
5. **API Connection:** Using **Retrofit**.
6. **UI/UX:** Everything related to App design and usability, using a **SingleActivity** and leveraging the *Navigation Component* to handle other *Fragments* and their *ViewModels*.

### Data:

In this module, we have included everything related to data:

1. **Datasources:** Definition of the *remote* and *local* interfaces.
2. **Repository:** Implementation of the repository interfaces.

### Domain:

In this module, we have included everything related to the business logic:

1. **Data:** Business classes corresponding to cells and boards.
2. **Errors:** Definition of errors that may occur in the application.
3. **Repository:** Definition of repositories.
4. **Use Cases:** Definition of use cases to be implemented in the *ViewModels*.

### AppTestShare:

This is an additional module to assist us in testing the application.

## App Functionality:

In the following video, you can see how the App works, following these steps:

1. **Board Creation:** When the application starts for the first time, there is no default selected board, and the list of boards is empty. Therefore, it is necessary to create a new board. To do this, click on the *Toolbar* to access the empty list, then click the add button to create a new board. You will need to assign a name, the number of rows and columns, and finally, a distinctive icon for the list.

2. **Board Editing:** Once the board is created, you will enter the **editing mode**, where you can assign a value to the different cells to use it later. By clicking on each cell, you can edit its text and its pictograph (essential for the application). From here, you can also delete the board.

3. **Board Usage:** Since this is the first board we created, if we go back to the list, we will see that it has been marked with a star, indicating that it is the default selection and will appear the next time we start the application. If you select different cells, you will see how the top bar is filled, where you can make corrections if you make a mistake or clear everything. By clicking on the speak icon, the application will **pronounce** the text in each cell, allowing the user to communicate.

<p align="center">
<img src="https://github.com/fesave/AAC-Board/blob/develop/media/example_aac.gif" width="250" />
</p>

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