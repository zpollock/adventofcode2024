from pathlib import Path

class Day:
    def process(self, input_data, is_part_two = False):
        raise NotImplementedError("Subclasses must implement the `process` method!")

    def parse_input_by_lines(self, input_data):
        return input_data.strip().split("\n")

    def read_input(self, filename):
        filepath = Path("resources") / filename
        with open(filepath, "r") as file:
            return file.read()
        