import importlib

def main():    
    #Day1
    day_processor = getDayProcessor("day1")    
    result = day_processor.process("d1_1_sample.txt")
    assert result == '11'
    result = day_processor.process("d1_1.txt")
    print(f"Result: {result}")

    result = day_processor.process("d1_1_sample.txt", True)
    assert result == '31' 
    result = day_processor.process("d1_1.txt", True) 
    print(f"Result: {result}")
    
    #Day2
    # day_processor = getDayProcessor("day2")    
    # result = day_processor.process("d2_1_sample.txt") 
    # assert result == None
    # result = day_processor.process("d2_1.txt")
    # print(f"Result: {result}")

    # result = day_processor.process("d2_1_sample.txt", True)
    # assert result == None
    # result = day_processor.process("d2_1.txt", True) 
    # print(f"Result: {result}")
        
def getDayProcessor(day_module):
    try:
        module = importlib.import_module(f"days.{day_module}")        
        class_name = day_module[0].upper() + day_module[1:]
        day_class = getattr(module, class_name)        
        return day_class()

    except (ImportError, AttributeError) as e:
        print(f"Error: Could not load the day module '{day_module}': {e}")
        return None
    
if __name__ == "__main__":
    main()
