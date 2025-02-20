import libGrafoKt.DirectedGraph
import libGrafoKt.Vertex
import libGrafoKt.Kruskal
import java.io.File

fun main() {
    val graph = DirectedGraph()
    val verticesMap = mutableMapOf<String, Vertex>()
    var vertexId = 0

    // println("Ingrese la ruta del archivo de texto con el grafo:")
    // val filePath = readLine()
    val filePath = "./test.txt"

    try {
        File(filePath).forEachLine { line ->
            val (source, destination, cost) = line.split(" ")
            val sourceVertex = verticesMap.getOrPut(source) { Vertex(vertexId++, source) }
            val destVertex = verticesMap.getOrPut(destination) { Vertex(vertexId++, destination) }

            graph.addVertex(sourceVertex)
            graph.addVertex(destVertex)
            graph.addEdge(sourceVertex, destVertex, cost.toInt())
        }

        val kruskal = Kruskal(graph)
        val minimumSpanningTree = kruskal.findMinimumSpanningTree()

        println("\nCadena más óptima:")
        minimumSpanningTree.forEach { edge ->
            println(edge)
        }
    } catch (e: Exception) {
        println("Error al procesar el archivo: ${e.message}")
    }
}
