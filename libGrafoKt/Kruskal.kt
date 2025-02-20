package libGrafoKt

class Kruskal(private val graph: DirectedGraph, private val startVertex: Vertex? = null) {
    private val parent = mutableMapOf<Vertex, Vertex>()
    private val rank = mutableMapOf<Vertex, Int>()

    private fun find(vertex: Vertex): Vertex {
        if (parent[vertex] != vertex) {
            parent[vertex] = find(parent[vertex]!!)
        }
        return parent[vertex]!!
    }

    private fun union(x: Vertex, y: Vertex) {
        val xRoot = find(x)
        val yRoot = find(y)

        when {
            rank[xRoot]!! < rank[yRoot]!! -> parent[xRoot] = yRoot
            rank[xRoot]!! > rank[yRoot]!! -> parent[yRoot] = xRoot
            else -> {
                parent[yRoot] = xRoot
                rank[xRoot] = rank[xRoot]!! + 1
            }
        }
    }

    fun findMinimumSpanningTree(): List<Edge> {
        val result = mutableListOf<Edge>()
        val sortedEdges = graph.getEdges().sortedBy { it.cost }

        graph.getVertices().forEach { vertex ->
            parent[vertex] = vertex
            rank[vertex] = 0
        }

        for (edge in sortedEdges) {
            val x = find(edge.source)
            val y = find(edge.destination)

            if (x != y) {
                result.add(edge)
                union(x, y)
            }
        }

        return result
    }

    fun findOptimalCycle(): List<Vertex> {
        // Verify if graph is Eulerian
        val degrees = mutableMapOf<Vertex, Int>()
        graph.getVertices().forEach { vertex ->
            degrees[vertex] = 0
        }
        graph.getEdges().forEach { edge ->
            degrees[edge.source] = degrees[edge.source]!! + 1
            degrees[edge.destination] = degrees[edge.destination]!! + 1
        }
        if (degrees.values.any { it % 2 != 0 }) {
            throw IllegalStateException("Graph is not Eulerian - cannot find Eulerian cycle")
        }

        // Use provided start vertex, USB vertex if exists, or first vertex as fallback
        val actualStart = startVertex ?: graph.getVertices().find { it.name == "USB" }
            ?: graph.getVertices().first()

        // Build adjacency map
        val adjacencyMap = mutableMapOf<Vertex, MutableList<Vertex>>()
        graph.getEdges().forEach { edge ->
            adjacencyMap.getOrPut(edge.source) { mutableListOf() }.add(edge.destination)
            adjacencyMap.getOrPut(edge.destination) { mutableListOf() }.add(edge.source)
        }

        // Hierholzer's algorithm
        val cycle = mutableListOf<Vertex>()
        val stack = ArrayDeque<Vertex>()
        stack.addLast(actualStart)

        while (stack.isNotEmpty()) {
            val current = stack.last()
            if (adjacencyMap[current]?.isNotEmpty() == true) {
                val next = adjacencyMap[current]!!.removeAt(0)
                stack.addLast(next)
            } else {
                cycle.add(stack.removeLast())
            }
        }

        // Reverse to get correct order
        cycle.reverse()

        return cycle
    }
}
