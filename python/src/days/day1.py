import io
from pathlib import Path
from days.day import Day

class Day1(Day):
    
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


    def part_one(self, reader) -> str:
        left_list = []
        right_list = []
        for line in reader:
            input_values = [int(value) for value in line.split()]
            left_list.append(input_values[0])
            right_list.append(input_values[1])

        left_list.sort()
        right_list.sort()

        similarity = sum(abs(left - right) for left, right in zip(left_list, right_list))
        return str(similarity)

    def part_two(self, reader) -> str:
        left_list= []
        right_map = {}
        for line in reader:
            input_values = [int(value) for value in line.split()]
            left_list.append(input_values[0])
            value = input_values[1]
            right_map[value] = right_map.get(value, 0) + 1

        similarity = sum(value * right_map.get(value, 0) for value in left_list)
        return str(similarity)
