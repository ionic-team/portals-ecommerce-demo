import UIKit

class QuantityPickerInputView: UITextField, UIPickerViewDataSource, UIPickerViewDelegate {
    private lazy var pickerToolbar: UIToolbar = {
        let toolbar = UIToolbar(frame: CGRect(x: 0, y: 0, width: UIScreen.main.bounds.width, height: 50))
        let spacer = UIBarButtonItem(barButtonSystemItem: .flexibleSpace, target: nil, action: nil)
        let button = UIBarButtonItem(barButtonSystemItem: .done, target: self, action: #selector(done(_:)))
        toolbar.items = [spacer, button]
        toolbar.sizeToFit()
        return toolbar
    }()
    
    private lazy var pickerView: UIPickerView = {
        let pickerView = UIPickerView()
        pickerView.dataSource = self
        pickerView.delegate = self
        return pickerView
    }()
    
    var selectionAction: ((UInt) -> Void)?
    var selectedQuantity: UInt = 0
    
    // MARK: - UITextField
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setup()
    }
    
    convenience init() {
        self.init(frame: .zero)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override var canBecomeFirstResponder: Bool {
        return true
    }
    
    // MARK: - Public
    
    func show() {
        becomeFirstResponder()
        pickerView.selectRow(Int(selectedQuantity), inComponent: 0, animated: false)
    }
    
    @IBAction private func done(_ sender: Any?) {
        selectionAction?(selectedQuantity)
        resignFirstResponder()
    }
    
    // MARK: - UIPickerViewDataSource
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return 10
    }
    
    // MARK: - UIPickerViewDelegate
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if row == 0 {
            return NSLocalizedString("Remove", comment:"Quantity choice for removal")
        }
        return String(format: NSLocalizedString("%u", comment:"Quantity choice for a number"), row)
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        selectedQuantity = UInt(row)
    }
    
    // MARK: - Private
    
    private func setup() {
        self.inputView = pickerView
        self.inputAccessoryView = pickerToolbar
        isHidden = true
    }
}
