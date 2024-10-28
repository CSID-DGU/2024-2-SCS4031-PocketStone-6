import { forwardRef, useState } from 'react';
import DatePicker from 'react-datepicker';

interface CustomInputProps {
  value?: string;
  onClick?: () => void;
  className?: string;
}

export function DatePickerComponent() {
  const [startDate, setStartDate] = useState<Date | null>(new Date());

  const ExampleCustomInput = forwardRef<HTMLButtonElement, CustomInputProps>(
    ({ value, onClick, className }, ref) => (
      <button className={className} onClick={onClick} ref={ref}>
        {value}
      </button>
    )
  );

  return (
    <>
      <DatePicker
        selected={startDate}
        onChange={(date: Date | null) => setStartDate(date)}
        customInput={<ExampleCustomInput className="example-custom-input" />}
      />
    </>
  );
}
