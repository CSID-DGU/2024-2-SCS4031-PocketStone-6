import { useOneEmployeeSpecQuery } from 'hooks/useOneEmployeeSpecQuery';
import { BS, MS } from 'styles';

interface ModalProps {
  id: number;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function EmployeeSpecModal({ id, setShowModal }: ModalProps) {
  const specQuery = useOneEmployeeSpecQuery(id);

  return (
    <div className={MS.modalBackground}>
      <div className={MS.modalBox}>
        <button
          className={BS.WhiteBtn}
          onClick={() => {
            setShowModal(false);
          }}>
          X
        </button>
        <p>{JSON.stringify(specQuery?.data)}</p>
      </div>
    </div>
  );
}
