# packt-freebie

A simple program written in Clojure that claims the free e-book of the day from packt.

## Installation

In order to create a runnable jar-file, simply stand in the project catalog and type (leiningen required) :

   $ lein uberjar

## Usage

After a jar-file has been created, run it using the following syntax:

    $ java -jar packt-freebie-0.1.0-standalone.jar [email] [pass]

packt-freebie will login to your account and claim the free e-book of the day.
It has no scheduler, so you need to put in in a crontab or such in order to run it daily.

## Options

email - email address for the registered account
pass - password

## Examples

$ java -jar packt-freebie-0.1.0-standalone.jar your@email.com password

### Future functionality

Ability to download the book as pdf after the book has been claimed.

## License

Distributed under the GPLv3 (https://www.gnu.org/licenses/gpl-3.0.html) license. Have fun.