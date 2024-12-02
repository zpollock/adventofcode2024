import io
from pathlib import Path
from days.day import Day

class Day2(Day):
    
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
        num_safe = 0
        for line in reader:
            input_values = [int(value) for value in line.split()]
            num_safe += is_safe(input_values)
        return num_safe

    def part_two(self, reader) -> str:
        num_safe = 0
        for line in reader:
            input_values = [int(value) for value in line.split()]
            num_safe += is_safe_with_dampener(input_values)
        return num_safe

def is_safe(report) -> int:
    if len(report) < 2: return 1
    
    if report[0] < report[1]:
        return all(report[i + 1] - report[i] > 0 and report[i + 1] - report[i] <= 3 for i in range(len(report) - 1))
    elif report[0] > report[1]:
        return all(report[i] - report[i + 1] > 0 and report[i] - report[i + 1] <= 3 for i in range(len(report) - 1))
    
    return 0

def is_safe_with_dampener(report: list[int]) -> int:
    if report[0] == report[1]:
        return is_safe(report[1:])

    bad_levels = set()
    if report[0] < report[1]:
        for i in range(len(report) - 1):
            if not (0 < report[i + 1] - report[i] <= 3):
                bad_levels.add(i)
                bad_levels.add(i + 1)
    else:
        for i in range(len(report) - 1):
            if not (0 < report[i] - report[i + 1] <= 3):
                bad_levels.add(i)
                bad_levels.add(i + 1)

    if not bad_levels:
        return 1

    bad_levels.add(0)
    for level in bad_levels:
        if is_safe(report[:level] + report[level + 1:]):
            return 1

    return 0    
    