#!/bin/bash

year=$1
day=$2

if [[ -z "${year}" || -z "${day}" ]]; then
  echo "usage: ${0} <year> <day>"
  exit 42
fi

mainPkg=src/main/java/year${year}/day${day}/
main=${mainPkg}Year${year}Day${day}.java

testPkg=src/test/java/year${year}/day${day}/
test=${testPkg}Year${year}Day${day}Test.java

resPkg=src/main/resources/year${year}/day${day}/
res1=${resPkg}/example.txt
res2=${resPkg}/data.txt

echo $year $day

echo ${main}
mkdir -p ${mainPkg}
cat > ${main} << EOF
package year${year}.day${day};

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Year${year}Day${day} {

    public static int solvePart1(String data) throws IOException {
        return 99;
    }

    public static int solvePart2(String data) throws IOException {
        return 99;
    }
}
EOF

echo ${test}
mkdir -p ${testPkg}
cat > ${test} << EOF
package year${year}.day${day};

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static utils.Utils.*;

public class Year${year}Day${day}Test {

    @Test
    void testPart1Example() throws IOException {
        assertEquals(111, Year${year}Day${day}.solvePart1(file("example.txt")));
    }

    @Test
    void testPart1() throws IOException {
        assertEquals(222, Year${year}Day${day}.solvePart1(file("data.txt")));
    }

    @Test
    void testPart2Example() throws IOException {
        assertEquals(333, Year${year}Day${day}.solvePart2(file("example.txt")));
    }

    @Test
    void testPart2() throws IOException {
        assertEquals(444, Year${year}Day${day}.solvePart2(file("data.txt")));
    }
}
EOF

mkdir -p ${resPkg}
echo ${res1}
touch ${res1}

echo ${res2}
touch ${res2}

echo ""
git add ${main}
git add ${test}
git add ${res1}
git add ${res2}
git status -sb
