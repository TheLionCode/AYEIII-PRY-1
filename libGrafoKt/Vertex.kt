package libGrafoKt

data class Vertex(val id: Int, val name: String) {
    override fun toString(): String = name
}
