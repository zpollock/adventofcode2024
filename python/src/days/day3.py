import re
from pathlib import Path
from days.day import Day

class Day3(Day):
    
    def process(self, input_filename: str, is_part2: bool = False) -> str:
        try:
            file_path = Path("resources") / input_filename
            with open(file_path, "r", encoding="utf-8") as reader:
                return self.part_two(reader) if is_part2 else self.part_one(reader)

        except FileNotFoundError as e:
            print(f"Error: Input file '{input_filename}' not found.")
            return "Error"


    def part_one(self, reader) -> str:
        product_sum = 0
        regex = r"mul\((\d+)\,(\d+)\)"

        for line in reader:
            product_sum += part_one_helper(line.strip(), regex)

        return str(product_sum)
    
    def part_two(self, reader):    
        product_sum = 0
        regex = r"mul\((\d+)\,(\d+)\)"

        line = process_line(reader.read())
        product_sum += part_one_helper(line, regex)

        return str(product_sum)

def process_line(line):
    while "don't()" in line:
        dont_index = line.index("don't()")
        do_index = line.find("do()", dont_index + 6)
        if do_index > 0:
            line = line[:dont_index] + line[do_index + 4:]
        else:                
            line = line[:dont_index]

    return line

def part_one_helper(line, regex):
    product_sum = 0
    for match in re.finditer(regex, line):
        num1, num2 = map(int, match.groups())
        product_sum += num1 * num2

    return product_sum
