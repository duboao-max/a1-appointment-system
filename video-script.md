# PROG2004 A1 视频讲解逐字讲稿（≤ 5 分钟）

> 使用说明：下面每一段都是你可以直接照着念的话。方括号 `[ ]` 里的内容是你的操作提示，不要念出来。
> 建议语速中等偏慢，全程录屏 + 你本人声音讲解即可，不必出镜。

---

## 开场（约 20 秒）

大家好，这是我的 PROG2004 Assessment 1——一个健康服务预约系统，用 Java 编写。

这个系统做的事很简单：病人可以向不同类型的健康专业人士预约当天的就诊时段。我设计了 9 个类，它们协作完成建模、预约管理，并防止同一个医生被重复预约。

今天我会重点讲清楚，面向对象编程的四大支柱——抽象、封装、继承、多态——是怎么在我的代码里落地，以及我为什么在那些地方这样设计。

---

## 第一支柱：抽象 Abstraction（约 50 秒）

[屏幕切到 HealthProfessional.java，高亮 `public abstract class` 这一行]

第一个支柱是**抽象**。

抽象的意思是：把一类事物共同的、本质的特征抽出来，形成一个"模板"，而把具体差异留给子类去实现。

在我的代码里，`HealthProfessional` 就是一个抽象类。不管是全科医生、儿科医生、护士还是营养师，他们都共享同样的信息：一个纯数字的 ID、一个姓名，以及每周固定的工作日。

[指向 `abstract` 关键字和 `getSpecialty()` 抽象方法]

我把它声明成 `abstract`，并且定义了一个抽象方法 `getSpecialty()`。这样做有一个好处：它**阻止别人直接 new 一个"没有任何专科"的泛化健康专业人士**——因为现实中根本不存在这样的人。任何具体类型都必须继承它、并实现自己的 `getSpecialty()`，否则代码根本编译不过去。这就让我的设计更健壮。

---

## 第二支柱：封装 Encapsulation（约 50 秒）

[屏幕切到 HealthProfessional.java 的字段和 getter 部分]

第二个支柱是**封装**。

封装的意思是：把数据藏起来，只通过受控的方法暴露出去，防止外部代码把对象放进一个非法状态。

[指向 `private final` 字段]

你看，我所有的字段都是 `private`，并且除了 `workingDays` 之外都是 `final`——一旦构造完成就不能改。外部代码只能通过 `getId()`、`getName()`、`getWorkingDays()` 这些 getter 来读取，没有 setter，所以没人能随意篡改。

[指向构造器的校验代码]

更关键的是，我在构造器里做了校验：比如 ID 必须只包含数字，姓名为空会直接抛异常。这样，一个对象要么被正确创建，要么根本创建不出来——它永远不会处于"ID 是空的"这种半吊子状态。

[可选：指向 `Collections.unmodifiableSet`]

另外，工作日我用了一个不可修改的集合来存储，返回给外部时也是只读视图，进一步保证了数据安全。

---

## 第三支柱：继承 Inheritance（约 50 秒）

[屏幕切到 GeneralPractitioner.java，高亮 `extends HealthProfessional`]

第三个支柱是**继承**。

继承的意思是：子类自动获得父类的字段和方法，从而复用代码，而不是重复写一遍。

[切到四个子类文件，逐个快速扫过 extends 那一行]

我的 `GeneralPractitioner`、`Pediatrician`、`Nurse` 和 `Dietitian` 都 `extends HealthProfessional`。它们**自动继承了** ID、姓名、工作日的存储和访问逻辑，以及 `toString`、`equals` 这些通用行为。

[指向某个子类自己独有的字段，比如 Nurse 的 registrationLevel]

每个子类只需要加自己**特有**的东西。比如护士多一个注册等级，儿科医生多一个接诊年龄段。共享的信息在父类写一次就够了，没有重复代码。这正是继承的价值：改一次父类，所有子类都受益。

---

## 第四支柱：多态 Polymorphism（约 60 秒）

[屏幕切到 AssignmentOne.java，高亮 `List<HealthProfessional>` 和 for-each 循环]

第四个支柱是**多态**。

多态的意思是：同一个调用，针对不同的对象，表现出不同的行为。

[指向 professionals 列表和 `System.out.println(professional)`]

在我的 main 方法里，我把九种不同类型的专业人士，全部放进一个 `List<HealthProfessional>` 里——注意，这里用的是**父类类型**来统一存储和处理。然后我只用一个 for-each 循环、一句 `println` 就打印了所有对象。

神奇的地方在于：虽然 `println` 调用的是同一个 `toString()`，但每个对象都因为各自覆写了 `getSpecialty()` 和 `getDetails()`，而打印出了**各自不同的细节**——医生显示"可以开药"，护士显示"注册等级、可抽血"，营养师显示专长方向。

[可选：指向 findType 泛型方法，说明它也是多态]

这就是多态的核心价值：我可以"面向父类型编程"，将来再加一种新的专业人士类型，我几乎不需要改动任何处理逻辑——只要新类继承 `HealthProfessional`，它就能自动融入这个体系。这也是作业里问的"如果要加新类型，代码要改多少"的答案：几乎不用改。

---

## 结尾（约 30 秒）

[切回项目结构或运行输出]

总结一下：我用**抽象**定义了一个健康专业人士的共同模板，用**封装**保护了数据不被破坏，用**继承**让四种子类型复用了共享代码，最后用**多态**让它们能被统一地、正确地处理和展示。

这就是我的预约系统。谢谢观看。

---

## 附：四支柱 → 代码位置速查表（方便你复习和应对提问）

| 支柱 | 关键代码 | 具体位置 |
|---|---|---|
| 抽象 | `public abstract class HealthProfessional` | `HealthProfessional.java` 类声明 |
| 抽象 | `public abstract String getSpecialty();` | `HealthProfessional.java` |
| 封装 | `private final String id;` 等字段 | `HealthProfessional.java` 字段区 |
| 封装 | 构造器里的 `if (id == null || !id.matches("\\d+"))` | `HealthProfessional.java` 构造器 |
| 封装 | `Collections.unmodifiableSet(...)` | `HealthProfessional.java` 构造器 |
| 继承 | `extends HealthProfessional` | 四个子类的类声明 |
| 继承 | `super(id, name, workingDays)` | 四个子类的构造器 |
| 多态 | `List<HealthProfessional> professionals` | `AssignmentOne.java` |
| 多态 | for-each 里的 `System.out.println(professional)` | `AssignmentOne.java` |
| 多态 | 覆写 `getSpecialty()` / `getDetails()` | 四个子类 |

---

## 录屏操作清单

1. 打开 IDE，把项目加载进来。
2. 先展示项目文件树（9 个类）。
3. 按上面的顺序切文件、高亮对应行，边讲边指。
4. 最后运行一次 `AssignmentOne`，把控制台输出展示出来（体现系统真实跑通）。
5. 总时长控制在 4 分半左右，留出缓冲。
