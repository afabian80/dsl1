package afabian.dsl

import groovy.xml.MarkupBuilder

class MemoDsl {
	String toText
	String fromText
	String body
	def sections = []

	def static void main(String[] args) {
		def memoFile = file(args[0])
		if(!memoFile.exists()) {
			println "Input file error (add as argument)"
			return
		}
		println memoFile.canonicalPath
	}

	def static make(clojure) {
		MemoDsl memoDsl = new MemoDsl()
		clojure.delegate = memoDsl
		clojure()
	}

	def to(String toText) {
		this.toText = toText
	}

	def from(String fromText) {
		this.fromText = fromText
	}

	def body(String bodyText) {
		this.body = bodyText
	}

	def methodMissing(String methodName, args) {
		def section = new Section(title: methodName, body: args[0])
		sections << section
	}

	def getXml() {
		doXml(this)
	}

	private static doXml(MemoDsl memoDsl) {
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		xml.memo() {
			to(memoDsl.toText)
			from(memoDsl.fromText)
			body(memoDsl.body)
			memoDsl.sections.each { s ->
				"$s.title"(s.body)
			}
		}
		println writer
	}
}