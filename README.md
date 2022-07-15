# <p align="center"><img src="src/main/resources/readme/medias/icon.png" width="140"> test-injection</p>

<h4 align="center">AKA. test-injection.</h4>
<p align="center">
 <a href="https://github.com/amaroteam/test-injection/actions/workflows/pipeline-dev.yml"><img alt="Actions Status" src="https://github.com/amaroteam/test-injection/actions/workflows/pipeline-dev.yml/badge.svg?branch=master"></a> 
 <a href="https://github.com/amaroteam/test-injection/actions/workflows/pipeline-prod.yml"><img alt="Actions Status" src="https://github.com/amaroteam/test-injection/actions/workflows/pipeline-prod.yml/badge.svg"></a>
  <a href="https://github.com/angular/angular.js/blob/master/DEVELOPERS.md#type"><img alt="Semantic Release" src="https://img.shields.io/badge/%20%20%F0%9F%93%A6%F0%9F%9A%80-semantic--release-e10079.svg"></a>
</p>

This component is a base sample to be used when creating a new component, the following topics will 
guide the steps needed to take for the creation of a new component, each one of them is important so 
follow them carefully.

- [test-injection](#test-injection)
  - [Table of Contents](#table-of-contents)
  - [1 - Changing the component's name](#1---changing-the-components-name)
    - [1.1 - Application.kt](#11---applicationkt)
    - [1.2 - pom.xml](#12---pomxml)
    - [1.3 - kubernetes (folder)](#13---kubernetes-folder)
    - [1.4 - docker-compose.yml](#14---docker-composeyml)
    - [1.5 - Dockerfile](#15---dockerfile)
    - [1.6 - pipeline-prod.yml & pipeline-dev.yml](#16---pipeline-prodyml--pipeline-devyml)
  - [2 - Dependencies](#3---dependencies)
    - [2.1 - Update a dependency](#31---update-a-dependency)
    - [2.2 - Add a new dependency](#32---add-a-new-dependency)
  - [3 - Architecture](#4---architecture)
    - [3.1 - gateways](#41---gateways)
      - [3.1.1 - controllers](#411---controllers)
      - [3.1.2 - externalinterfaces](#412---externalinterfaces)
      - [3.1.3 - repositories](#413---repositories)
    - [3.2 - usecases](#42---usecases)
      - [3.2.1 - ports](#421---ports)
    - [3.3 - domains](#43---domains)
    - [3.4 - config](#44---config)
  - [4 - Creating tests](#5---creating-tests)
  - [5 - Booting Up](#6---booting-up)
To start developing your component, you'll first need to change this base
component's name. It is referenced in many files, so after changing the 
folder's name, you'll also need to make the same change in them. 

These are the references to the component's name:

```/src/main/kotlin/com/amaro/ecp/testinjection/TestInjectionApplication.kt ```

Your main class should have its name changed to something like: **YourComponentsName**Application.

Inside the file, in the **&lt;artifactId>** and **&lt;name>** tag.

(Although not obligatory, the description of the component should also be changed)

All file names in this folder should contain the component's name.

Inside the file, the following values should be changed:

**services.cerberus.container_name.**
**services.cerberus.image.**
**services.cerberus.networks.**

Inside the file, in the last argument of **CMD** and the .jar files in the line before the 
**EXPOSE** command. 

Inside both files, change the values in **env.APP_NAMESPACE** and **env.APP_NAME**.

The addition of dependencies should be made through Maven. All
dependencies are listed in the **pom.xml** file, such as many properties of the project. 

For every change made involving Maven you should run the following command on the terminal, which 
will reload all dependencies and run all tests currently on your component. 

```shell
$ ./mvnw clean install
```

*PS: The command used above depends on spring native. In case your component doesn't use Spring Native,
you should use the following command:*

```shell
$ ./mvnw clean install
```

With the dependency installed, if you wish to update any of them to the latest version, you search
for them through [Maven's Website](https://mvnrepository.com), searching using the artifactId.
It is **extremely important** that you always keep
dependencies up to date, in their latest stable release. Keeping a dependency outdated could expose
your component to vulnerabilities, which could compromise not only your component but many backend 
functionalities.

To add a dependency, just insert them in the **&lt;dependencies>** tag. A dependency
has the following format: 

```xml
<dependency>
	<groupId> ORGANIZATION OF THE DEPENDENCY </groupId>
	<artifactId> DEPENDENCY ID </artifactId>
	<version> VERSION OF THE DEPENDENCY </version>
</dependency>
```

Our components' architecture is based on Clean-Arch. Although not following it
completely, most of the concepts are the same.

Under the component's main folder we have 4 divisions:

Classes responsible for communicating with outside services, which can be
the front-end or maybe another system (e.g. Redis). It contains the DTOs and the
logic for service-specific implementations.
Bellow is the defined sub-packages that will be in gateway package.

*NOTE: that all cases are already defined, in this case you can bring new options to be discussed in 
the backend chapter.*

This package will hold all controllers and rest endpoints that the component provides. All classes in 
this package will end with "Controller", e.g. "FooterController", and all communication through 
controllers must be done by a DTO (data transfer object). So you shall create a package called *dto* 
and put all dtos used by the controllers.

*NOTE: Usecases cannot receive DTOs, so they need to know how to convert to domains to be sent to 
the usecases.*

This package will hold the port implementation that makes external calls to anothers components or 
systems. All implementation in this package will end with "EI" (that means External Interface), 
e.g. "HybrisProductListEI"

This package will hold the port implementation that makes database requests. All implementation in 
this package will end with the database name (like "Postgres", "Redis", etc...), e.g. 
"CacheFooterRedis", and all communication through database must be done by a Model class that 
references to the database tables. So you shall create a package called models and put all models 
used by the repositories.

*NOTE: Usecases cannot receive these models, so they need to know how to convert to domains to be 
sent to the usecases.*

Classes responsible for the component's functions, but being unaware of their
specific implementation. Usecases can only know domains, so you mustn't send DTOs, Models or other 
data classes. All implementation in this package will end with "UserCase" e.g. "GetTranslatedReviewsUseCase".

Ports are interfaces that will be used by Usecases in order to access the component's functions. 
These interfaces can have many implementations using different services, but neither the Usecases
nor the Ports should be aware or which one is being used or their internal logic. All classes in 
this package will end with "Port", e.g. "TranslateReviewsPort"

Domains are entities that contemplates a business domain, they must not be based on database models 
or Data Transfer Objects.

Classes for configuration of features utilized in other divisions, including constants 
that'll be used as well.

It is very important that, for every feature that is implemented, unit tests are added.To develop 
tests, it is highly recommended that you use JUnit 5.

Tests are implemented on the same relative path that the class we wish to test,
for example, a class created in:

```src/main/kotlin/com/amaro/ecp/testinjection/```

should be tested in:

```src/test/kotlin/com/amaro/ecp/testinjection/```

After all the preparation is done, you should run the application by running the main class 
(**TestInjectionApplication**) and wait for the initialization to be completed.

There's currently only one endpoint for testing: 
```locahost:8080/hello``` 

If everything goes correctly, the component should go up and the message
confirming that it is on air should show up.