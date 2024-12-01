import io
from pathlib import Path
from days.day import Day

class Day25(Day):
    
    def process(self, input_filename: str, is_part2: bool = False) -> str:
        try:
            file_path = Path("resources") / input_filename
            with open(file_path, "r", encoding="utf-8") as reader:
                return self.part_two(reader) if is_part2 else self.part_one(reader)

        except FileNotFoundError as e:
            print(f"Error: Input file '{input_filename}' not found.")
            return "Error"
        except Exception as e:
            print(f"An error occurred: {e}")
            return "Error"


    # Not yet implemented
    def part_one(self, reader) -> str:
        for line in reader:
            pass
        return None

    # Not yet implemented
    def part_two(self, reader) -> str:
        for line in reader:
            pass
        return None
