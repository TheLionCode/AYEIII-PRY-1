package libGrafoKt

import libGrafoKt.Vertex
import libGrafoKt.Edge

class DirectedGraph {
    private val vertices = mutableSetOf<Vertex>()
    private val edges = mutableListOf<Edge>()

    fun addVertex(vertex: Vertex) {
        vertices.add(vertex)
    }

    fun addEdge(source: Vertex, destination: Vertex, cost: Int) {
        edges.add(Edge(source, destination, cost))
    }

    fun getVertices(): Set<Vertex> = vertices
    fun getEdges(): List<Edge> = edges
}
