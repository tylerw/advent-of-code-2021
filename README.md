# Advent of Code 2021

## Installation

Download from https://github.com/tylerw/advent-of-code-2021

## Usage

Solve a particular day (requires Babashka):

    $ bb solve 1

Run the project's tests (they'll fail until you edit them):

    $ clojure -T:build test

Run the project's CI pipeline and build an uberjar (this will fail until you edit the tests to pass):

    $ clojure -T:build ci

This will produce an updated `pom.xml` file with synchronized dependencies inside the `META-INF`
directory inside `target/classes` and the uberjar in `target`. You can update the version (and SCM tag)
information in generated `pom.xml` by updating `build.clj`.

If you don't want the `pom.xml` file in your project, you can remove it. The `ci` task will
still generate a minimal `pom.xml` as part of the `uber` task, unless you remove `version`
from `build.clj`.

Run that uberjar:

    $ java -jar target/advent-of-code-2021-0.1.0-SNAPSHOT.jar

If you remove `version` from `build.clj`, the uberjar will become `target/advent-of-code-2021-standalone.jar`.

## License

Copyright © 2021 Tyler Wardhaugh

Distributed under the Eclipse Public License version 1.0.
