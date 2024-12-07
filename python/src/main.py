import importlib

def main():    
    #Day7
    day_processor = getDayProcessor("day7")    
    result = day_processor.process("d7_1_example.txt") 
    assert result == '3749'
    result = day_processor.process("d7_1.txt")
    print(f"Result: {result}")

    result = day_processor.process("d7_1_example.txt", True)
    assert result == '11387'
    result = day_processor.process("d7_1.txt", True) 
    print(f"Result: {result}")
        
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
