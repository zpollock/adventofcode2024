import io
from pathlib import Path
from days.day import Day

class Day7(Day):
    
    def process(self, input_filename: str, is_part2: bool = False) -> str:
        try:
            file_path = Path("resources") / input_filename
            with open(file_path, "r", encoding="utf-8") as reader:
                return parse_input(reader, is_part2)

        except FileNotFoundError as e:
            print(f"Error: Input file '{input_filename}' not found.")
            return "Error"
        except Exception as e:
            print(f"An error occurred: {e}")
            return "Error"


def parse_input(reader, is_part2):
    lines = [line.strip().split() for line in reader]
    # lines = []
    # for line in reader:
    #     lines.append(line.strip().split())

    result = calibration_helper(lines, is_part2)
    return str(result)


def calibration_helper(lines, is_part2):
    result = 0
    for line in lines:
        value = int(line[0][:-1])
        if is_valid_test(line, 1, 0, value, is_part2):
            result += value
    return result


def is_valid_test(line, pos, exp_value, value, is_part2):
    if pos == len(line):
        return exp_value == value

    curr_value = int(line[pos])

    if is_valid_test(line, pos + 1, exp_value + curr_value, value, is_part2):
        return True

    if is_valid_test(line, pos + 1, curr_value if exp_value == 0 else exp_value * curr_value, value, is_part2):
        return True

    if is_part2 and pos <= len(line) - 1:
        concatenated_value = int(str(exp_value) + str(curr_value))
        if is_valid_test(line, pos + 1, concatenated_value, value, is_part2):
            return True

    return False
