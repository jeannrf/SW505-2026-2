#!/bin/bash
set -e

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd "$DIR"

mkdir -p build/classes
echo "Compilando clases Java..."
javac -d build/classes src/*.java

echo "Ejecutando Sistema Académico..."
java -cp build/classes Main
