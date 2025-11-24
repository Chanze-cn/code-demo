<!--
Sync Impact Report:
Version change: N/A → 1.0.0 (initial version)
Modified principles: N/A (initial creation)
Added sections: Core Principles (5 principles), Development Workflow, Quality Gates, Governance
Removed sections: N/A
Templates requiring updates:
  ✅ plan-template.md - Constitution Check section aligns with principles
  ✅ spec-template.md - Requirements section aligns with testing/documentation principles
  ✅ tasks-template.md - Task organization aligns with module management and testing principles
  ✅ checklist-template.md - No constitution-specific references found
  ✅ agent-file-template.md - No constitution-specific references found
Follow-up TODOs:
  - RATIFICATION_DATE: TODO - Original adoption date unknown, needs historical research or project start date
-->

# Code Demo Constitution

## Core Principles

### I. Module Management (Maven Multi-Module Structure)
所有新增子模块必须同步更新根目录 `pom.xml` 的 `<modules>` 配置，遵循现有的多模块 Maven 结构。每个模块应保持独立性和清晰的职责边界。`java-concurrency/` 目录仅用于 Java 并发与管程相关示例；若要扩展其他主题，应创建新的功能子模块。

**Rationale**: 多模块结构有助于代码组织、依赖管理和构建优化。明确的模块边界避免职责混乱，便于维护和扩展。

### II. Code Standards (Java 编码规范)
遵循 README 中的约定：类名使用 PascalCase，方法与变量使用 camelCase，常量使用 UPPER_SNAKE_CASE，包名全小写。使用 2 个空格缩进，单行长度不超过 120 字符，花括号遵循 K&R 风格。关键类与方法必须提供 JavaDoc；复杂逻辑需要补充中文行内注释。

**Rationale**: 统一的编码规范提高代码可读性和可维护性，JavaDoc 和注释有助于知识传承和代码理解。

### III. Testing Discipline (测试驱动开发)
新增或修改并发逻辑时，务必在对应模块的 test 文件夹下补充或更新对应的 JUnit 5 测试。运行测试及构建命令参考 README：`mvn clean compile`、`mvn test`，提交前需至少运行相关模块的测试。若引入新测试依赖，应在模块 `pom.xml` 中声明，并确保与父级依赖版本兼容。

**Rationale**: 测试是代码质量的保障，特别是并发代码的正确性验证。测试驱动开发有助于设计更好的 API 和更健壮的实现。

### IV. Documentation Requirements (文档与笔记规范)
新增或修改笔记需保持中文撰写，标题层级、术语格式参照现有 Markdown 规范。笔记中的配图统一存放在对应目录的 `image/` 子目录，禁止直接引用网络图片。每次项目更新生成时，需要根据本次更新内容，同步更新 `README.md` 文件中的内容。`README.md` 文件中的内容要求是对整个项目的概况，目前包括代码实践模块、面试考题模块、学习笔记模块。

**Rationale**: 文档是知识传承的重要载体，统一的格式和结构便于查找和维护。README 的及时更新确保项目概览的准确性。

### V. Version Control Practices (版本控制规范)
提交信息使用清晰的中文描述，并在适当情况下引用受影响的模块或文件。分支策略遵循 `main`、`feature/*`、`bugfix/*` 的命名约定；新功能或修复应在对应分支完成后再合并。提交前确认未包含本地 IDE 配置、日志或其他临时文件。

**Rationale**: 清晰的提交信息和规范的分支策略有助于代码审查、问题追踪和项目历史管理。

## Development Workflow

### 工作流程
1. 创建功能分支：`git checkout -b feature/feature-name` 或 `git checkout -b bugfix/bug-name`
2. 开发过程中遵循代码规范和测试要求
3. 提交前运行相关测试：`mvn test`
4. 提交代码时使用清晰的中文提交信息
5. 合并到主分支前进行代码审查

### 模块添加流程
1. 创建新的子模块目录
2. 在子模块中创建 `pom.xml`，配置父模块引用
3. 更新根目录 `pom.xml` 的 `<modules>` 配置
4. 更新 `README.md` 中的项目结构说明
5. 添加相应的测试和文档

## Quality Gates

### 代码质量检查点
- **编译检查**: 所有模块必须能够成功编译 (`mvn clean compile`)
- **测试检查**: 相关模块的测试必须通过 (`mvn test`)
- **代码规范**: 遵循 Java 编码规范和命名约定
- **文档完整性**: 新增功能需更新 README 和相关文档

### 提交前检查清单
- [ ] 代码编译通过
- [ ] 代码相关单元测试已编写
- [ ] 代码相关集成测试已通过
- [ ] 代码符合编码规范
- [ ] JavaDoc 和注释完整
- [ ] README 已更新（如适用）
- [ ] 提交信息清晰明确
- [ ] 未包含临时文件或 IDE 配置

## Governance

### 宪法地位
本宪法文件优先于所有其他开发实践和约定。所有 PR 和代码审查必须验证是否符合宪法原则。任何违反原则的复杂性必须被明确论证其必要性。

### 修订程序
1. 提出修订建议，说明修订原因和影响范围
2. 评估修订对现有代码和流程的影响
3. 更新宪法文件，同步更新版本号
4. 更新所有相关的模板文件（plan-template.md、spec-template.md、tasks-template.md 等）
5. 通知团队成员并更新开发指南

### 版本管理
宪法版本遵循语义化版本控制（Semantic Versioning）：
- **MAJOR**: 向后不兼容的治理原则移除或重新定义
- **MINOR**: 新增原则或章节，或实质性扩展指导
- **PATCH**: 澄清、措辞修正、拼写错误修复、非语义性改进

### 合规审查
- 所有新功能和模块添加必须通过宪法检查
- 代码审查时需验证是否符合核心原则
- 定期审查项目结构是否符合模块管理原则
- 检查文档更新是否符合文档规范

**Version**: 1.0.0 | **Ratified**: TODO(RATIFICATION_DATE): 需要确定项目开始日期或首次采用日期 | **Last Amended**: 2025-11-24
