package day0

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import netscape.javascript.JSObject
import java.io.File

fun main() {

    val lines = {}.javaClass.getResourceAsStream("/recipes.txt").bufferedReader().readLines()
    val recipes: MutableList<Recipe> = mutableListOf()

    val linesToRead = 17
    for (i in 0 .. lines.size / 17) {

        val mealType = lines[i * 17].substringAfter("Sure, here are 15 ").substringBefore(" recipe ideas:")
        for (j in 1 until linesToRead - 1) {
            val line = lines[i * linesToRead + j]
            recipes.add(
                Recipe(mealType = mealType, meal = line.substringBefore(":"), instruction = line.substringAfter(": "))
            )
        }
    }

    // format json with indents
    val formattedJson = jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(recipes)

    // write file safe similar to local getResourceAsStream into resource folder
    File("src/main/resources/recipes.json").writeText(formattedJson)
}

data class Recipe(val mealType: String, val meal: String, val instruction: String)
