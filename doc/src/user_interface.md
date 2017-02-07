# User Interface {#user_interface}

## Introduction
The user interface consists of three components, a set of FXML files that 
describe the UI markup, a set of Cascading Style Sheets (CSS) that describe 
the UI styling, and a collection of Spring beans that work as controllers.

## FXML Markup

[JavaFX](http://docs.oracle.com/javase/8/javase-clienttechnologies.htm) 
provides XML-based markup for graphical elements as an alternative to 
instantiating graphics in business logic. A consequence of this is that 
concerns between UI markup and business logic remain well-separated. At boot
 time, an [FXMLLoader](http://bit.ly/2jDLLPg) loads the ``Application.fxml``
  file. This file is the entry point for the entire UI.
  
Each tag represents a class. Each attribute in the tag represents a 
constructor argument to the class, annotated with ``@NamedArg``.

The ``<fx:include>`` tag is used to include other FXML files. The 
``<stylesheets>`` section is used to import CSS into the document.

The ``<fx:controller>`` tag is used to indicate the controller that must be 
instantiated in order to manage the particular element.
 
The [FXMLLoader](http://bit.ly/2jDLLPg) is configured such that it uses
``<ReturnType> ReturnType context.getBean(Class<ReturnType> requiredType)`` to
 do this.

Use the ``styleClass`` attribute to specify a string or an array of strings 
representing the CSS classes to which the element belongs. Unlike in HTML, 
CSS class names need to be separated by a comma and space, instead of 
whitespace.

## CSS

[Cascading Style Sheets (CSS)](http://bit.ly/1G0YUfU) work similarly to CSS 
in HTML. Referencing elements by style class is done by preceding the class 
name with a ``.`` and referencing elements by id is done by preceding the 
name with a ``#``. However, JavaFX defines its formatting keys differently 
than standard CSS. For more information, see the 
[Oracle JavaFX CSS Guide](http://bit.ly/2jYG0PE).

## How to Make a UI Component

1. Create an FXML file
2. Create a CSS file to style it
3. Create a controller and place a reference to the controller in the FXML file
4. Instantiate the controller in ``UserInterfaceConfiguration.java`` as a bean
