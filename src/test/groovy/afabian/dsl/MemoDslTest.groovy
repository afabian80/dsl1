package afabian.dsl

class MemoDslTest extends GroovyTestCase {

	void testUsageWithXmlOutput() {
		MemoDsl.make {
			to "All personnel"
			from "Akos Fabian"
			body "What's up, doc?"
			idea "This is something funny"
			deadline "today"
			xml
		}
	}
}