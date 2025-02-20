package libGrafoKt

data class Edge(val source: Vertex, val destination: Vertex, val cost: Int) {
    override fun toString(): String = "${source}->${destination}($cost)"
}
