# Refactoring Java

## 🚀 Following modifications were performed

#### ✅ Added
- ✔️ Added `pom.xml` to have predictable behavior, configurable dependencies and Java version.
- ✔️ Added unit tests `RentalInfoTest.java`.
- ✔️ Added packages `com.testtask.informationslip.dto`, `com.testtask.informationslip.dao`, `com.testtask.informationslip.constant`, `com.testtask.informationslip.exception`, `com.testtask.informationslip.manager` to the projects. All classes now placed to the layers where their beasness logic is represented.
- ✔️ Added class `MovieRentalManager.java`. It responsible to handle add record about rented movie to customer or to retrieve rented by customer movies. That class helped to remove hardcoded and useless logic of creating rented movies data inside `RentalInfo`.java
- ✔️ Added runtime exception `MovieRentalDuplicationException.java`. It will be trown by `MovieRentalManager` if someone will try to rent a movie that has already rented.
- ✔️ Added enum `MovieRentalData.java`. That is a clas of constants that we use inside `RentalInfo.statement(customer)` generation. Using constant makes the `statement()` method much more simple and professional. 
- ✔️ Added Id to `Movie.java`. The `MovieRental.java` has reference to `Movie.id`. Adding `id` to `Movie.java` make current handling logic more understandable and clear.

#### 🔄 Changed
- 🔹 Updated `pom.xml` to support Junit 4.
- 🔹 Refactored structure of project according to java project standards - created `src/main` and `src/test` folders.
- 🔹 Refactored `RentalInfo.java` to improve design and to fix the problem with single responsibility there.
- 🔹 Refactored `Customer.java`, `Movie.java`,`MovieRental.java`. These classes refactored as `record` according to the new abilities of java 17. Using `record` definition makes the code smaller and makes it more stable and thread safe.
- 🔹 Refactored `RentalInfo.java`. The statement() method was significantly simplified and become auto extended using `MovieRental.java`.

#### 🛠️ Fixed
- 🐛 Fixed `Movie.java`. Variable `String code;` has been refactored to `MovieRentalData code;`. Constant variable has a lot of advantages if compare it with String variable. For example, for enumeration we make sure we have only correct variiables for that variable. We can't garany that if we use a Struing variable. 
- 🐛 Fixed `RentalInfo.java` A quite bad approach to concatenate strings like `str1 + str2 +str3 etc` replaced by usage of a `StringBuilder`. 

#### ❌ Removed
- 🗑️ Class `Main.java`. That class contains only simple, hardcoded, useless test. The standard Unit tests were added insted. 

## Handing in the assignment

The assignment is represented as a pull-request on github and as ZIP-file.
The original code is placed in the `master` branch. The refactoring is placed in the `refactoring-by-sergiy` branch.
https://github.com/sergeybezzub/refactoring-java-master

## To run the test:
```
mvn -Dtest=RentalInfoTest  test
```
